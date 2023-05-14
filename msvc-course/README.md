# Getting Started

## Properties file
- To run Dockerfile, it is necessary to update the reference of localhost in the `application.properties` file
- Replace `localhost` by `host.docker.internal`
- And also the reference in the `src/main/java/com/jcalvopinam/msvc/course/client/UserClientRest.java` class
- Compile again: `./mvnw package -Dmaven.test.skip `

## Set-up Java 17
```shell
sdk install java 17.0.6-librca 
# or
sdk use java 17.0.6-librca 
```

## Compile the project
```shell
./mvnw clean install -Dmaven.test.skip
```

## Docker database
```shell
docker run --name postgresdb -e POSTGRES_PASSWORD=juanca -p 5432:5432 -d postgres:15.2
```
- To start the database
```shell
docker start postgresdb
```
- To create a new Docker network
```shell
docker network create spring
```
- To build msvc-course image go to the sample-msvc-kubernetes folder:
```shell
docker build -t msvc-course:1.0.0 . -f ./msvc-course/Dockerfile
```
- To run an instance of the image:
```shell
docker run -p 8002:8080 -d --rm --name microservice-course --network spring msvc-course:1.0.0
```
- To overwrite the internal port
```shell
docker run -p 8002:8092 -e APP_PORT=8092 -d --rm --name microservice-course --network spring msvc-course:1.0.0
```
- To read the environments from a file is necessary to create a file called `.env` and add the variables, after run:
```shell
docker run -p 8002:8002 --env-file=./msvc-course/.env -d --rm --name microservice-course --network spring msvc-course:1.0.0
```
- To inspect the environment variables
```shell
docker inspect microservice-course | grep -A 10 'Env'  
```