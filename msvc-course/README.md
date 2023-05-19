# Getting Started

## Properties file
- To run the `Dockerfile` is necessary to update the reference of localhost in the `application.properties` file
- Search for the key `spring.datasource.url`
- Replace `localhost` by `host.docker.internal`
- And also the `url` property of Feign client `src/main/java/com/jcalvopinam/msvc/course/client/UserClientRest.java`
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

## FeignClient configuration
- The feign annotation has the following structure of the target microservice
```java
@FeignClient(name = "name-of-the-microservice", url = "name-of-the-microservice-docker-instance")
```
- The `name` property must be the same name defined in the `application.properties` file under the key `spring.application.name`
- The `url` property must be the same name of the running docker instance for the microservice

In this case for this project:
- The user microservice has defined the name `msvc-user-web`
- The instance was created with the name `msvc-user-cont`
- And the port number is `8001`
```java
@FeignClient(name = "msvc-user-web", url = "msvc-user-cont:8001")
```
- Those values can be externalized in the `application.properties` file and also in the `.env` file
```java
@FeignClient(name = "${msvc.user.application.name}", url = "${msvc.user.application.url}")
```

## Docker

### Docker database
```shell
docker run -p 5432:5432 -d \
  -e POSTGRES_PASSWORD=juanca \
  -e POSTGRES_DB=msvc_course_db \
  -v data-postgres:/var/lib/postgresql/data \
  --restart always \
  --name postgresdb \
  postgres:15.2-alpine
```
- To start the database
```shell
docker start postgresdb
```
- To stop the database
```shell
docker stop postgresdb
```

### Docker Network
- To create a new Docker network (if not already created)
```shell
docker network create sample_msvc_kubernetes_net
```

### Docker Course
- To build msvc-course image go to the sample-msvc-kubernetes folder:
```shell
docker build -t sample-msvc-kubernetes/msvc-course:1.0.0 . -f ./msvc-course/Dockerfile
```
- To run an instance of the image:
```shell
docker run -p 8002:8080 -d --rm \
  --name msvc-course-cont \
  --network sample_msvc_kubernetes_net \
  sample-msvc-kubernetes/msvc-course:1.0.0
```
- Sample to overwrite the internal port
```shell
docker run -p 8002:8092 -d --rm \
  --name msvc-course-cont \
  -e APP_PORT=8092 \
  --network sample_msvc_kubernetes_net \
  sample-msvc-kubernetes/msvc-course:1.0.0
```
- To read the environments from a file is necessary to create a file called `.env` and add the variables, after run:
```shell
docker run -p 8002:8002 -d --rm \
  --name msvc-course-cont \
  --env-file=./msvc-course/.env \
  --network sample_msvc_kubernetes_net \
  sample-msvc-kubernetes/msvc-course:1.0.0
```
- To inspect the environment variables
```shell
docker inspect msvc-course-cont | grep -A 12 'Env'  
```