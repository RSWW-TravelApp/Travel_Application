# Travel_Application

## Local mode
### Prerequisites
- Mongodb Server (install from official webpage https://www.mongodb.com/docs/manual/installation/)
- Mongo Shell (install from official webpage https://www.mongodb.com/docs/mongodb-shell/install/)
- RabbitMQ (install from official webpage https://www.rabbitmq.com/download.html)

## Local Docker mode
### Prerequisites
- Mongodb Server
- Mongo Shell
- RabbitMQ
- Docker
- Docker compose plugin
- python3
- pip

### 1. Fetch sources and build application sources
```
git clone git@github.com:RSWW-TravelApp/Travel_Application.git
cd ./Travel_Application
mvn clean install -U
```

### 2. Start MongoDB Server:
```
mongod --dbpath /path/to/your/database --port 27017 --bind_ip 172.17.0.1
```

### 3. Build service images and run application
```
docker compose up -d // To build application with health checks [RECOMMENDED] 
OR
docker compose -f docker-compose-dev.yml -d up // To build application without health checks
```

### 4. Init data in services
```
pip install requests
python3 -m init-data
```

### 5. Reach application on localhost:10000 via browser 



