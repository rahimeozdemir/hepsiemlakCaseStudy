FROM openjdk:21-jdk

ADD target/todolist-app.jar todolist-app.jar
ENTRYPOINT ["java", "-jar", "/todolist-app.jar"]