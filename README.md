# Getting Started

## Docker compose
- To create and start the services go to the sample-msvc-kubernetes folder:
```shell
docker-compose up -d
```
- To start the services go to the sample-msvc-kubernetes folder:
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