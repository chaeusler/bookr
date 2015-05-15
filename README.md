# bookr

## Intellij setup
./gradlew idea or import gradle project


## Application Server Setup
### Datasource
Needs PersistenceUnit named "bookr"

<datasource jta="true" jndi-name="java:jboss/datasources/BookrDS" pool-name="BookrDS" enabled="true" use-ccm="true">
    <connection-url>jdbc:h2:mem:bookr;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false</connection-url>
    <driver-class>org.h2.Driver</driver-class>
    <driver>h2</driver>
    <security>
        <user-name>sa</user-name>
        <password>sa</password>
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

