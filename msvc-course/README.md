# Getting Started

## Properties file
- To run Dockerfile, it is necessary to update the reference of localhost in the `application.properties` file
- Replace `localhost` by `host.docker.internal`
- And also the reference in the `src/main/java/com/jcalvopinam/msvc/course/client/UserClientRest.java` class
- Compile again: `./mvnw package -Dmaven.test.skip `

## Docker database
```shell
docker run --name postgresdb -e POSTGRES_PASSWORD=juanca -p 5432:5432 -d postgres:15.2
```

- To build msvc-course image go to the sample-msvc-kubernetes folder:
```shell
docker build -t msvc-course:1.0.0 . -f ./msvc-course/Dockerfile
```

- To run an instance of the image:
```shell
docker run -p 8002:8002 msvc-course:1.0.0
```
