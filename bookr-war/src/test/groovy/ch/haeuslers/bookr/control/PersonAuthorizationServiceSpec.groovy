package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.JBossLoginContextFactory
import ch.haeuslers.bookr.entity.Person
import ch.haeuslers.bookr.entity.PersonAuthorization
import ch.haeuslers.bookr.entity.RoleType
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.ejb.EJBAccessException
import javax.inject.Inject
import javax.security.auth.login.FailedLoginException
import javax.security.auth.login.LoginContext

import static ch.haeuslers.bookr.control.SecurityUtils.*;

@RunWith(ArquillianSputnik.class)
class PersonAuthorizationServiceSpec extends Specification {
    @Deployment
    def static WebArchive "create deployment"() {
        ShrinkWrap.create(WebArchive.class, 'PersonAuthorizationServiceSpec.war')
            .addClass(PersonService.class)
            .addClass(PersonAuthorizationService.class)
            .addClass(PasswordService.class)
            .addPackage(Person.class.getPackage())
            .addClass(JBossLoginContextFactory.class)
            .addClass(SecurityUtils.class)
            .addAsWebInfResource("META-INF/jboss-ejb3.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsResource("users.properties")
            .addAsResource("roles.properties")
    }

    @Inject
    PersonService personService

    @Inject
    PersonAuthorizationService authorizationService

    def "crud as admin"() {
        setup:
        LoginContext loginContext = loginAsAdministrator()

        String personsId = UUID.randomUUID().toString()
        Person person = new Person(principalName: "nameForAuthorizationTest", id: personsId)
        PersonAuthorization authorization = new PersonAuthorization(person: person)

        when: "create user and authorization is created"
        doWith(loginContext) {
            personService.create(person)
            authorizationService.create(authorization)
        }

        then: "the authorization can be found with the users id"
        PersonAuthorization foundAuthorization = doWith(loginContext) {
            authorizationService.read(personsId).get()
        }
        foundAuthorization.equals(authorization)
        foundAuthorization.roles.isEmpty()

        when: "the authorization is changed"
        foundAuthorization.roles.add(RoleType.USER)
        foundAuthorization = doWith(loginContext) {
            authorizationService.update(foundAuthorization)
            authorizationService.read(personsId).get()
        }

        then: "the reread object has been updated"
        foundAuthorization.roles.contains(RoleType.USER)
        foundAuthorization.roles.size() == 1

        when: "the autorization is deleted"
        doWith(loginContext) {
            authorizationService.delete(authorization)
        }

        then: "it can't be found anymore"
        Optional<PersonAuthorization> optional = doWith(loginContext) {
            authorizationService.read(personsId)
        }
        !optional.present

        cleanup:
        loginContext.logout()
    }

    def "unauthorized user - user"() {
        setup:
        LoginContext loginContext = loginAsUser()

        when:
        doWith(loginContext) {
            authorizationService.read("anything")
        }

        then:
        thrown EJBAccessException
    }

    def "unauthorized user - manager"() {
        setup:
        LoginContext loginContext = loginAsManager()

        when:
        doWith(loginContext) {
            authorizationService.read("anything")
        }

        then:
        thrown EJBAccessException
    }

}