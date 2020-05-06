# kvb rad 

## commands

Compile and run via: `mvn spring-boot:run`

Create jar, create docker image and run container: 

```shell
# build application
mvn clean package -Dmaven.test.skip=true
docker build -t crowdsalat/kvb-rad .

# create docker network
docker network create --driver bridge pgnetwork

# create volume so db will be saved
docker volume create --driver local --name=pgvolume

# create docker container for postgres
docker run -d --rm \
--name pg-container \
-p 5432:5432 \
-e POSTGRES_PASSWORD=postgres \
--network=pgnetwork \
--volume=pgvolume:/pgdata \
postgres:latest

# create a docker container for application
docker run -p 8080:8080 --name kvb-rad --network=pgnetwork crowdsalat/kvb-rad 
```
mvn clean package -Dmaven.test.skip=true && docker build `

## features

- polls the position of the kvb bikes every 5 minutes and save there current position
- saves the history of kvb bike for 24 hours 
- calculates and saves the shortest route between the two positions if a bike was moved between two polls


## api consumption

### url

This project uses the nextbike api for cologne: [https://api.nextbike.net/maps/nextbike-official.json?city=14]. *Alternatively there is a GBFS (General Bikeshare Feed Specification) conform api under: [https://api.nextbike.net/maps/gbfs/v1/nextbike_kg/gbfs.json]*

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
- use AOP to log all method calls when application is set to debug
- save date with timezone and greater precision

  
