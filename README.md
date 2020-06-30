# kvb rad 

The application polls the position of the kvb bikes every minute and saves it in a [mysql](https://www.mysql.com/) database. It uses a selfhosted [graphhopper](https://www.graphhopper.com/) server to  calculate the shortest route between the two positions of a bike if it was moved between two polls. To view the data you can use the web application [kvb-rad-ui](https://github.com/CrowdSalat/kvb-rad-ui).

## commands

- Compile and run with h2 via: `mvn spring-boot:run -Dspring.profiles.active=dryrun`
- Compile and run via: `mvn spring-boot:run`
- Compile: `mvn clean package -Dmaven.test.skip=true`
- Compile and build docker container locally: `mvn clean package -Dmaven.test.skip=true && docker build -t crowdsalat/kvb-rad .`
- Start application with docker-compose: `docker-compose up -d`
- Start application with headless docker-compose: `docker-compose --file ./docker-compose-headless.yml up -d`
- Stop application: `docker-compose down`

Start application with docker: 

```shell
# create docker network
docker network create --driver bridge kvbbike

# create volume so db will be saved
docker volume create --driver local --name=kvbbike-db

# start mysql
docker run -d -p 3306:3306 --name kvb-mysql -network kvbbike -e MYSQL_ROOT_PASSWORD=myql -e MYSQL_DATABASE=kvbbike-db -v kvbbike-db:/var/lib/mysql mysql:latest

#start graphhopper
docker run -d -p 8989:8989 --name graphhopper --network kvbbike crowdsalat/graphhopper-cologne-bike

# start the application
docker run -d -p 8080:8080 --name kvb-rad --network=kvbbike crowdsalat/kvb-rad 
```

## api consumption

### graphhopper

This project uses a locally hosted [Graphhopper](https://www.graphhopper.com/de/) server to calculate the distances and the waypoints of a bike movement. 
The route api is explained [here](https://github.com/graphhopper/graphhopper/blob/master/docs/web/api-doc.md). 
The waypoints are encoded to reduce bandwidth. To decode them there is a official [java] (https://github.com/graphhopper/graphhopper/blob/d70b63660ac5200b03c38ba3406b8f93976628a6/web/src/main/java/com/graphhopper/http/WebHelper.java#L43) 
and a official [javascript](https://github.com/graphhopper/graphhopper/blob/d70b63660ac5200b03c38ba3406b8f93976628a6/web/src/main/webapp/js/ghrequest.js#L139) library from graphhopper.

### kvb

This project uses the nextbike api for cologne: [https://api.nextbike.net/maps/nextbike-live.json?city=14]. 
Alternatively there is a GBFS (General Bikeshare Feed Specification) conform api under: [https://api.nextbike.net/maps/gbfs/v1/nextbike_kg/gbfs.json]

### used libraries

The Rest API is consumed via spring **RestTemplate** which uses Jackson (JSON Processor) annotations on java classes. The model classes for json were scaffolded with the vscode plugin [Paste JSON as Code](https://marketplace.visualstudio.com/items?itemName=quicktype.quicktype) from [Quicktype](https://github.com/quicktype/quicktype).

Alternatives for the use of RestTemplate would be:

- Jersey (JAX-RS)
- Spring WebClient from the WebFlux Stack (newer)

## logging

Logging is done via slf4j with the default spring boot configuration.

## data 

The data is saved vie spring data repositories and jpa entities. 

There is a MappedSuperclass AbstractEntity which provides the following generated fields: 

- uuid 
- creationDate (uses spring data annotation @CreatedDate, @EnableJpaAuditing and @EntityListeners(AuditingEntityListener.class))
- lastModified

The uuid works because of the hibernate annotation @GenericGenerator. 
The fields creationDate and lastModified use the spring data annotation @CreatedDate and @LastModifiedDate as well as the **AuditingEntityListener**. To activate the listener a configuration class is annotated with **@EnableJpaAuditing**.

## TODO

- global exception handling: [different methods](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- save date with timezone and greater precision
- look at transactions
- handle bike points which could not been calculated
- handle bikes which are not in cologne: org.springframework.web.client.HttpClientErrorException$BadRequest: 400 Bad Request: [{"message":"Point 0 is out of bounds: 51.450448888889,7.0227255555556","hints":[{"message":"Point 0 is out of bounds: 51.450448888889,7.0227255555556","details":"com.graphhopper.util.exceptions.PointOutOfBoundsException","point_index":0}]}]

  
