# ariviv

Ariviv is an application built with Kotlin, Ktor & GraphQL. The data is stored in-memory, without the use of a database.

## Starting the application

To start the application, simply run the following commands to configure the environment variables and build up the container:
```
source .env
docker-compose up --build
```

It is also possible to not source the `.env` file and use the application's default configuration.
## Extras

|Status|Task|
|-|-|
[:heavy_check_mark:] | [Automated test](src/test/kotlin/com/ryanzidago)
[:heavy_check_mark:] | GraphQL Schema documentation generated with [graphdoc](https://github.com/2fd/graphdoc#static-page-generator-for-documenting-graphql-schema) available offline [here](src/main/resources/graphql/doc) or online [here](http://localhost:8080/graphql/doc/index.html)
[:heavy_check_mark:] | Containerisation with [Docker](Dockerfile) and [docker-compose](docker-compose.yml)