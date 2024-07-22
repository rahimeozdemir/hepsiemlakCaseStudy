### technologies project is created with:
* maven 3.9.8
* java 21
* couchbase 7.6.*
* docker 27.0.3

### build:
#### couchbase:
* download couchbase server to local machine https://www.couchbase.com/downloads/
* create a new cluster http://localhost:8091/ui/index.html
* register by giving username Administrator and password 123456 (see CouchbaseConfig.java file)
* when application started, a new bucked called todo_list and scopes(user, task) and collections(user,task) will be created automatically
#### todolist application:
* to create jar file in target folder run 'mvn clean package' command
* download docker desktop to local machine https://www.docker.com/products/docker-desktop/
* run following docker commands 
  * docker build -t todolistapp .
  * docker run -p 8090:8080 todolistapp
* go to http://localhost:8090/swagger-ui/index.html#/

### authentication:
* add a new user via http://localhost:8090/register
* login with the registered user information via http://localhost:8090/login and get JWT
* set JWT when using api as a Bearer Token
* JWT will be expired in 5 minutes. Increase the duration from JwtService#generateToken

### api usage:
* for to do list you can send requests to add, update, delete, list endpoints



