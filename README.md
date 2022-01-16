# ariviv

Ariviv is an application built with Kotlin, Ktor & GraphQL. The data is stored in-memory, without the use of a database.

## Starting the application

### With Docker & docker-compose

To start the application, simply run the following commands to configure the environment variables and build up the container:
```
source .env
docker-compose up --build
```

### With Gradlew
```
source .env
./gradlew run
```
It is also possible to not source the `.env` file and use the application's default configuration.

## Using the application
1. visit the GraphQL playground [endpoint](http://localhost:8080/graphql) 
2. create a user with the `registerUser` mutation and fetch its ID from the response
3. with the ID from the previously created user, send a `markExerciseSessionAsFinished` mutation
4. take a look at the log printed to the console or to the [log file](log/ariviv.log). After 3 seconds (assuming the `.env` file has been sourced, otherwise you will have to wait for the time period defined in the [application](src/main/kotlin/com/ryanzidago/Application.kt)), a reminder to exercise for the previously created user will be displayed

## Understanding how the appplication works
1. a user needs to register first
2. every registered user is automatically enrolled in a so called exercise routine (except for the users that were automatically seeded in the application)
3. their exercise routine only starts after they have marked their first exercise session as complete
4. if they have not marked an exercise routine as complete recently, a log message is printed to the console and sent to disc.

## Extras

|Status|Task|
|-|-|
[:heavy_check_mark:] | [Automated test](src/test/kotlin/com/ryanzidago)
[:heavy_check_mark:] | GraphQL Schema documentation generated with [graphdoc](https://github.com/2fd/graphdoc#static-page-generator-for-documenting-graphql-schema) available offline [here](src/main/resources/graphql/doc) or online [here](http://localhost:8080/graphql/doc/index.html)
[:heavy_check_mark:] | Containerisation with [Docker](Dockerfile) and [docker-compose](docker-compose.yml)
[:heavy_check_mark:] | Logs written to a file (see [logback config](src/main/resources/logback.xml))