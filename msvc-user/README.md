# Getting Started

## Properties file
- To run Dockerfile, it is necessary to update the reference of localhost in the `application.properties` file
- Replace `localhost` by `host.docker.internal`
- And also the reference in the `src/main/java/com/jcalvopinam/msvc/user/client/CourseClientRest.java` class
- Compile again: `./mvnw package -Dmaven.test.skip `

## Docker database
```shell
docker run --name mysqldb -e MYSQL_ROOT_PASSWORD=juanca -p 3306:3306 -d mysql:8
```

- To build msvc-user image go to the sample-msvc-kubernetes folder:
```shell
docker build -t msvc-user:1.0.0 . -f ./msvc-user/Dockerfile
```

- To run an instance of the image:
```shell
docker run -p 8001:8001 msvc-user:1.0.0
```
