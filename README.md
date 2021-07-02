# GraphQL Vertx demo

This application is demo of __GraphQL__ with Vert.x. It was based on [Implementing a GraphQL server Vert.x how-to.](https://how-to.vertx.io/graphql-howto/)
In this project I've applied _hexagonal architecture_ described based on [Allegro article.](https://blog.allegro.tech/2020/05/hexagonal-architecture-by-example.html)

## Starting application
To run this application you can use:
- `./gradlew run`,
- `GraphQL Application` service in IntelliJ.

## Endpoints:
- `http://localhost:8080/graphql` - main GraphQL HTTP endpoint,
- `ws://localhost:8080/graphql-ws` - main GraphQL WebSocket endpoint,
- `http://localhost:8080/grapihql/` - GraphiQL endpoint for GraphQL UI.

## Possible operations
For demo purposes, two operations were implemented:
```
# Query getAllTasks returns a list of all tasks (10 mocked at startup).
# Takes optional $uncompletedOnly param which allows to fetch only uncompleted tasks.
#
query getAllTasks($uncompletedOnly: Boolean! = false) {
  allTasks(uncompletedOnly: $uncompletedOnly) {
    id
    description
    completed
  }
}


# Mutation completeTask completes a task by given $id, returning whether completion was successful or not
#
mutation completeTask($id: String!) {
  complete(id: $id)
}
```

## Resources:
- https://how-to.vertx.io/graphql-howto/
- https://blog.allegro.tech/2020/05/hexagonal-architecture-by-example.html
