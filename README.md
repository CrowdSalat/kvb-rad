# kvb rad 

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

## TODO

- global exception handling: [different methods](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- use AOP to log all method calls when application is set to debug
- 

  
