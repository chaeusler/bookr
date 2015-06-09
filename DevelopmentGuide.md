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
It's laering is supervised by jdepend as Unit-Test.

According the file 'dependencyMatrix.csv' in the test resources, the dependencies are allowed as follows:

- boundary -> common, entity, control
- control -> common, entity
- entity -> common

# REST Conventions

TODO

