# Introduction
This is an educational project. Don't use it in production.

Technologies:

- Java EE7
- Angular JS
- Gradle
- Arquilllian
- Spock

Infrastructure:

- PostgreSQL
- Wildfly 8.2

# Intellij setup
./gradlew idea or import gradle project


# Application Server Setup
## add PostgreSQL JDBC driver and configure datasource
Download the JDBC driver.

Run Jboss and call jboss-cli.sh:

- module add --name=org.postgres --resources=/tmp/postgresql-9.4-1201.jdbc41.jar --dependencies=javax.api,javax.transaction.api
- /subsystem=datasources/jdbc-driver=postgres:add(driver-name="postgres",driver-module-name="org.postgres",driver-class-name=org.postgresql.Driver)
- data-source add --jndi-name=java:jboss/datasources/BookrDS --name=BookrDS --connection-url=jdbc:postgresql://localhost/bookr --jta=true --use-ccm=true --driver-name=postgres --user-name=bookr --password=bookr

## Additions in standalone.xml
                
### configure authentication
in `<security-domain>`:
       
    <security-domain name="secureDomain" cache-type="default">
      <authentication>
          <login-module code="Database" flag="required">
              <module-option name="dsJndiName" value="java:jboss/datasources/BookrDS"/>
              <module-option name="principalsQuery" value="SELECT p.password FROM BOOKR_PASSWORD AS p JOIN BOOKR_AUTHORIZATION AS a ON p.authorization_id = a.person_id WHERE a.principalName = ?"/>
              <module-option name="rolesQuery" value="SELECT ar.role as &quot;Role&quot;, 'Roles' as &quot;Roles&quot; FROM BOOKR_AUTHORIZATION a JOIN BOOKR_AUTHORIZATION_ROLE ar ON a.person_id = ar.authorization_id WHERE a.principalName = ?"/>
          </login-module>
      </authentication>
    </security-domain>

### configure loggng
in `<subsystem xmlns="urn:jboss:domain:logging:2.0">`:

      <periodic-rotating-file-handler name="BOOKR_PERFORMANCE_FILE" autoflush="true">
          <formatter>
              <named-formatter name="PATTERN"/>
          </formatter>
          <file relative-to="jboss.server.log.dir" path="bookr-performance.log"/>
          <suffix value=".yyyy-MM-dd"/>
          <append value="true"/>
      </periodic-rotating-file-handler>
      <periodic-rotating-file-handler name="BOOKR_AUDIT_FILE" autoflush="true">
          <formatter>
              <named-formatter name="PATTERN"/>
          </formatter>
          <file relative-to="jboss.server.log.dir" path="bookr-audit.log"/>
          <suffix value=".yyyy-MM-dd"/>
          <append value="true"/>
      </periodic-rotating-file-handler>
      <logger category="ch.haeuslers.bookr.common.performance.PerformanceLogger">
          <level name="TRACE"/>
          <handlers>
              <handler name="BOOKR_PERFORMANCE_FILE"/>
          </handlers>
      </logger>
      <logger category="ch.haeuslers.bookr.common.Auditor">
          <level name="TRACE"/>
          <handlers>
              <handler name="BOOKR_AUDIT_FILE"/>
          </handlers>
      </logger>

## enable https
follow [Configuring https] (http://blog.eisele.net/2015/01/ssl-with-wildfly-8-and-undertow.html)

# Database

The application is tested with PostgreSQL 9.4.


## install and configure on OS X
- brew install prostgres
- pg_ctl start -l $logfile ([Official Documentation] (http://www.postgresql.org/docs/9.4/static/server-start.html))
- initdb

## configure DB user and add database
call `psql 

- CREATE USER bookr PASSWORD 'bookr';
- CREATE DATABASE bookr OWNER bookr;

# build the distribution

## preconditions
- npm is installed
- wildfly is configured and running
- postresql is configured and running (postgres -D /usr/local/var/postgres)
  
## steps
- in 'booker-client' call: `npm install && bower install && gulp build`
- in 'bookr' call: gradle distribution

## outcome
The distribution is built as zip and tar into 'bookr-distribution/build/distriburions'
