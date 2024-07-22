package com.hepsiemlak.todo.config;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.manager.bucket.BucketSettings;
import com.couchbase.client.java.manager.bucket.BucketType;
import com.couchbase.client.java.manager.collection.CollectionSpec;
import com.hepsiemlak.todo.exception.BucketConfigException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.auditing.EnableCouchbaseAuditing;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Slf4j
@Configuration
@EnableCouchbaseAuditing
@EnableCouchbaseRepositories(basePackages = {"com.hepsiemlak.todo.repository"})
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {
    private static final String CONNECTION_STRING = "host.docker.internal"; // for connect to local database from docker container
    private static final String USERNAME = "Administrator";
    private static final String PASSWORD = "123456";
    private static final String BUCKET_NAME = "todo_list";
    private static final String USER_SCOPE = "user";
    private static final String USER_COLLECTION = "user";
    private static final String TASK_COLLECTION = "task";
    private static final String TASK_SCOPE = "task";

    @Override
    public String getConnectionString() {
        return CONNECTION_STRING;
    }

    @Override
    public String getUserName() {
        return USERNAME;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    public String getBucketName() {
        return BUCKET_NAME;
    }

    @Override
    @Bean(destroyMethod = "disconnect")
    public Cluster couchbaseCluster(ClusterEnvironment couchbaseClusterEnvironment) {
        try {
            log.info("Connecting to Couchbase cluster at " + CONNECTION_STRING);
            return Cluster.connect(getConnectionString(), getUserName(), getPassword());
        } catch (Exception e) {
            log.error("Error connecting to Couchbase cluster", e);
            throw e;
        }
    }

    @Bean
    public Bucket getCouchbaseBucket(Cluster cluster) {
        try {
            if (!cluster.buckets().getAllBuckets().containsKey(getBucketName())) {
                log.info("Bucket with name {} does not exist. Creating it now", getBucketName());
                cluster.buckets()
                        .createBucket(BucketSettings.create(BUCKET_NAME)
                                .bucketType(BucketType.COUCHBASE)
                                .ramQuotaMB(256)
                                .numReplicas(1)
                                .replicaIndexes(true)
                                .flushEnabled(true));

                var bucket = cluster.bucket(BUCKET_NAME);
                createScopeAndCollection(bucket, USER_SCOPE, USER_COLLECTION);
                createScopeAndCollection(bucket, TASK_SCOPE, TASK_COLLECTION);
            }
            return cluster.bucket(getBucketName());

        } catch (Exception e) {
            log.error("Error getting bucket", e);
            throw new BucketConfigException("Error getting bucket", e);
        }
    }

    private void createScopeAndCollection(Bucket bucket, String scopName, String collectionName) {
        try {
            // Check if the scope exists
            Scope userScope = bucket.scope(scopName);
            if (userScope != null) {
                log.info("Scope {} does not exist. Creating it now", scopName);
                bucket.collections().createScope(scopName);
                bucket.collections().createCollection(CollectionSpec.create(scopName, collectionName));
            }

        } catch (Exception e) {
            log.error("Error creating scope or collection. Scope: {}, Collection: {}", scopName, collectionName, e);
            throw new BucketConfigException("Error creating scope or collection", e);
        }
    }

}