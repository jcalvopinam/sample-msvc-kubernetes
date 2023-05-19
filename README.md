# Getting Started
All the commands must be run under the sample-msvc-kubernetes folder

## Docker compose
- To create and start the services:
```shell
docker-compose up -d
```
- To force to build the images:
```shell
docker-compose build
```
- To force to build, create and start the services:
```shell
docker-compose up --build -d
```
- To start the services:
```shell
docker-compose start
```
- To stop the services
```shell
docker-compose stop
```
- To stop and delete the services and volumes
```shell
docker-compose down -v
```
- To watch the logs
```shell
docker logs -f msvc-user-cont
docker logs -f msvc-course-cont
```