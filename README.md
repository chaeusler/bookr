# bookr

## Intellij setup
./gradlew idea or import gradle project


## Application Server Setup
### add PostgreSQL JDBC driver and configure datasource
Download the JDBC driver.

Run Jboss and call jboss-cli.sh:

- module add --name=org.postgres --resources=/tmp/postgresql-9.4-1201.jdbc41.jar --dependencies=javax.api,javax.transaction.api
- /subsystem=datasources/jdbc-driver=postgres:add(driver-name="postgres",driver-module-name="org.postgres",driver-class-name=org.postgresql.Driver)
- data-source add --jndi-name=java:jboss/datasources/BookrDS --name=BookrDS --connection-url=jdbc:postgresql://localhost/bookr --jta=true --use-ccm=true --driver-name=postgres --user-name=bookr --password=bookr

### Datasource
Needs PersistenceUnit named "bookr"

    <datasource jta="true" jndi-name="java:jboss/datasources/BookrDS" pool-name="java:jboss/datasources/BookrDS" enabled="true" use-ccm="true">
        <connection-url>jdbc:h2:mem:bookr;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false</connection-url>
        <driver-class>org.h2.Driver</driver-class>
        <driver>h2</driver>
        <security>
            <user-name>sa</user-name>
            <password>sa</password>
        </security>
    </datasource>
    
    <datasource jta="true" jndi-name="java:jboss/datasources/BookrDS" pool-name="BookrDS" enabled="true" use-ccm="true">
            <connection-url>jdbc:h2:mem:bookr;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false</connection-url>
            <driver-class>org.h2.Driver</driver-class>
            <driver>h2</driver>
            <security>
                <user-name>bookr</user-name>
                <password>bookr</password>
            </security>
        </datasource>
                
### security
       
    <security-domain name="secureDomain" cache-type="default">
      <authentication>
          <login-module code="Database" flag="required">
              <module-option name="dsJndiName" value="java:jboss/datasources/BookrDS"/>
              <module-option name="principalsQuery" value="SELECT p.password FROM BOOKR_PASSWORD AS p JOIN BOOKR_AUTHORIZATION AS a ON p.authorization_id = a.person_id WHERE a.principalName = ?"/>
              <module-option name="rolesQuery" value="SELECT ar.role as &quot;Role&quot;, 'Roles' as &quot;Roles&quot; FROM BOOKR_AUTHORIZATION a JOIN BOOKR_AUTHORIZATION_ROLE ar ON a.person_id = ar.authorization_id WHERE a.principalName = ?"/>
          </login-module>
      </authentication>
    </security-domain>


### https
[Configuring https] (http://blog.eisele.net/2015/01/ssl-with-wildfly-8-and-undertow.html)

## Database

The application is tested with PostgreSQL 9.4.


### install and configure on OS X
- brew install prostgres
- pg_ctl start -l logfile ([Official Documentation] (http://www.postgresql.org/docs/9.4/static/server-start.html))
- initdb

#### configure DB user and add database
with `psql  

- CREATE USER bookr PASSWORD 'bookr';
- CREATE DATABASE bookr OWNER bookr;


