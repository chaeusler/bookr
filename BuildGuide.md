# Build Setup
This document contains the instructions to build the distribution. If you want to develop the application please consult the [Development Guide](DevelopmentGuide.md)

## JDK 1.8
i.e. [Oracles JDK] (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Database
The application is tested with PostgreSQL 9.4.


## Wildfly 8.2
in standalone.xml add inside `<security-domains>`:

    <security-domain name="testing" cache-type="default">
        <authentication>
            <login-module code="UsersRoles" flag="sufficient"/>
        </authentication>
    </security-domain>

## Gradle 2.4
consider using [GVM] (http://gvmtool.net)

## npm
on OSX with `brew install npm`

# Build Distribution
## preconditions
- JDK 8 is installed
- gradle 2.4 is installed
- npm is installed 

- wildfly is configured and running according this guide
- postresql is configured and running (postgres -D /usr/local/var/postgres)
  
## steps
- in 'booker-client' call: `npm install && bower install && gulp build`
- Start your Application server
- in 'bookr' call: `gradle distributon -x test`

## outcome
The distribution is built as zip and tar into 'bookr-distribution/build/distributions'