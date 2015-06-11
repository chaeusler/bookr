# Build Setup

## JDK 1.8
i.e. [Oracles JDK] (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Database
The application is tested with PostgreSQL 9.4.

### install and configure on OS X
- brew install postgres
- pg_ctl start -l $logfile ([Official Documentation] (http://www.postgresql.org/docs/9.4/static/server-start.html))
- initdb

### configure DB user and add database
call `psql`

- CREATE USER bookr PASSWORD 'bookr';
- CREATE DATABASE bookr OWNER bookr;
- \q

## Wildfly 8.2

call `./setupWildfly.sh`

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
- in 'bookr' call: `gradle distributon`

## outcome
The distribution is built as zip and tar into 'bookr-distribution/build/distributions'