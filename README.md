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
          <module-option name="principalsQuery" value="SELECT pwd.password AS passwd FROM BOOKR_PASSWORD AS pwd JOIN BOOKR_PERSON AS p ON pwd.PERSON_ID = p.ID AND p.PRINCIPALNAME = ?"/>
          <module-option name="rolesQuery" value="SELECT par.role AS role, 'Roles' FROM BOOKR_PERSON p, BOOKR_PERSON_AUTHORIZATION_ROLE par WHERE p.id = par.person_authorization_id AND p.principalName = ?"/>
      </login-module>
  </authentication>
</security-domain>

