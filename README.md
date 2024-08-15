# Travel Application
Distributed application based on microservices principles. It gives proof of concept of high scalable distributed application with frontend done with native html/css/js and backend done with Java Webflux as core, MongoDB as database system and RabbitMQ as message broker system. Created according to design patterns such as: API Gateway, Discovery server, CQRS and Event sourcing.

## Prerequisites
- Mongodb Server
- Mongo Shell
- RabbitMQ
- Java JDK 17
- python3
- pip
- Docker
- Docker compose plugin

### 1. Fetch sources and build application sources
```
git clone git@github.com:RSWW-TravelApp/Travel_Application.git
cd ./Travel_Application
mvn clean install -U
pip install requests, pymongo
```

## Local
### 2. Start MongoDB Server with admin rights:
```
mongod --port 27017 --bind_ip 127.0.0.1
```

### 3. Initialize databases and run application
```
python -m db_init --ip 127.0.0.1
```

## Docker
### 2. Start MongoDB Server:
```
mongod --port 27017 --bind_ip <SPRING_DATA_MONGODB_URI>
```

### 3. Initialize databases
```
python -m db_init --ip <SPRING_DATA_MONGODB_URI>
```

### 4. Build service images and run application
```
docker compose up -d // To build application with health checks [RECOMMENDED] 
OR
docker compose -f docker-compose-dev.yml -d up // To build application without health checks
```

## Feed app with data
```
python3 -m init-data
```

## Reach application
- client on localhost:10000 via browser
- eureka server on localhost:8761



