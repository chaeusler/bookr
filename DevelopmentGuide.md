This Document contains informations how to develop the application. Additionally some architectural notes are contained.

Please follow the instructions in the [Build Guide](BuildGuide.md) first.

# Database Setup

## install and configure on OS X
- brew install postgres
- pg_ctl start -l $logfile ([Official Documentation] (http://www.postgresql.org/docs/9.4/static/server-start.html))
- initdb

## configure DB user and add database
call `psql`

- CREATE USER bookr PASSWORD 'bookr';
- CREATE DATABASE bookr OWNER bookr;
- \q

Then run `gradle flywayMigrate`

# Wildfly Setup
To get a production like environment it's recommended to apply the instructions from [the Distribution](bookr-distribution/src/std/dist/README.md)


# Project Topology
The whole project is organized in multiple modules and is built with gradle.

## Modules
Dependencies:
bookr-distribution -> bookr-war -> bookr-client

### bookr-client
Contains the Angular JS code and is built with gulp.

### bookr-war
Contains the Server side logic and APIs. It builds the JEE7 webapplication. 

All Java packages are under ch.haeuslers.bookr. 

#### Layering

The web application ist organized with 4 main packages.

- boundary: Contains the REST API and the Websocket server endpoints.
- control: Holds all business services.
- entity: Defines the persistent entities. These are enriched with marshalling und unmarshalling Annotations (JAXB).
- common: All other stuff like producers and auditing related logic.

It's layering is supervised by jdepend as Unit-Test.

According the file 'dependencyMatrix.csv' in the test resources, the dependencies are allowed as follows:

- boundary -> common, entity, control
- control -> common, entity
- entity -> common

# REST Conventions

The resources are all addressed by RESOURCE/id where id needs to be of the type uuid according to rfc4122.
When creating resources the id's need to be created by the client.

## create
url: RESOURCES/{id}

id: uuid

request method: PUT

request body: json or xml

response code: 204

response body: -

## read specific
url: RESOURCES/{id}

id: uuid

request method: GET

request body: -

response code: 200

response body: json or xml

## read all
url: RESOURCES

request method: GET

request body: -

response code: 200

response body: json or xml

## updated
url: RESOURCES/{id}

id: uuid

request method: POST

request body: -

response code: 204

response body: json or xml

## delete
url: RESOURCES/{id}

id: uuid

request method: DELETE

request body: -

response code: 204

response body: -