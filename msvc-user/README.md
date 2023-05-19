# Getting Started

## Properties file
- To run the `Dockerfile` is necessary to update the reference of localhost in the `application.properties` file
- Search for the key `spring.datasource.url`
- Replace `localhost` by `host.docker.internal`
- And also the `url` property of the Feign client `src/main/java/com/jcalvopinam/msvc/user/client/CourseClientRest.java`
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
- The course microservice has defined the name `msvc-course-web`
- The instance was created with the name `msvc-course-cont`
- And the port number is `8002`
```java
@FeignClient(name = "msvc-course-web", url = "msvc-course-cont:8002")
```
- Those values can be externalized in the `application.properties` file and also in the `.env` file
```java
@FeignClient(name = "${msvc.course.application.name}", url = "${msvc.course.application.url}")
```

## Docker

### Docker database
```shell
docker run -p 3306:3306 -d \
  -e MYSQL_ROOT_PASSWORD=juanca \
  -e MYSQL_DATABASE=msvc_user_db \
  -v data-mysql:/var/lib/mysql \
  --restart always \
  --name mysqldb \
  mysql:8
```
- To start the database
```shell
docker start mysqldb
```
- To stop the database
```shell
docker stop mysqldb
```

### Docker Network
- To create a new Docker network (if not already created)
```shell
docker network create sample_msvc_kubernetes_net
```

### Docker User
- To build msvc-user image go to the sample-msvc-kubernetes folder:
```shell
docker build -t sample-msvc-kubernetes/msvc-user:1.0.0 . -f ./msvc-user/Dockerfile
```
- To run an instance of the image:
```shell
docker run -p 8001:8080 -d --rm \
  --name msvc-user-cont \
  --network sample_msvc_kubernetes_net \
  sample-msvc-kubernetes/msvc-user:1.0.0
```
- Sample to overwrite the internal port
```shell
docker run -p 8001:8091 -d --rm \
  --name msvc-user-cont \
  -e APP_PORT=8091 \
  --network sample_msvc_kubernetes_net \
  sample-msvc-kubernetes/msvc-user:1.0.0
```
- To read the environments from a file is necessary to create a file called `.env` and add the variables, after run:
```shell
docker run -p 8001:8001 -d --rm \
  --name msvc-user-cont \
  --env-file=./msvc-user/.env \
  --network sample_msvc_kubernetes_net \
  sample-msvc-kubernetes/msvc-user:1.0.0
```
- To inspect the environment variables
```shell
docker inspect microservice-course | grep -A 12 'Env'  
```