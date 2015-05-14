package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.JBossLoginContextFactory
import ch.haeuslers.bookr.entity.Person
import ch.haeuslers.bookr.entity.Authorization
import ch.haeuslers.bookr.entity.Role
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.ejb.EJBAccessException
import javax.inject.Inject

@RunWith(ArquillianSputnik.class)
class PersonAuthorizationServiceSpec extends Specification {
    @Deployment
    def static WebArchive "create deployment"() {
        ShrinkWrap.create(WebArchive.class, 'PersonAuthorizationServiceSpec.war')
            .addClass(PersonService.class)
            .addClass(AuthorizationService.class)
            .addClass(PasswordService.class)
            .addPackage(Person.class.getPackage())
            .addClass(JBossLoginContextFactory.class)
            .addClass(LoginSession.class)
            .addClass(EntityManagerProducer.class)
            .addAsWebInfResource("META-INF/jboss-ejb3.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsResource("users.properties")
            .addAsResource("roles.properties")
    }

    @Inject
    PersonService personService

    @Inject
    AuthorizationService authorizationService

    def "crud as admin"() {
        setup:
        LoginSession session = LoginSession.loginAsAdministrator()

        UUID personsId = UUID.randomUUID()
        Person person = new Person(principalName: "nameForAuthorizationTest", id: personsId)
        Authorization authorization = new Authorization(person: person)

        when: "create user and authorization is created"
        session.call {
            personService.create(person)
            authorizationService.create(authorization)
        }

        then: "the authorization can be found with the users id"
        Authorization foundAuthorization = session.call {
            authorizationService.read(personsId.toString()).get()
        }
        foundAuthorization.equals(authorization)
        foundAuthorization.roles.isEmpty()

        when: "the authorization is changed"
        foundAuthorization.roles.add(Role.USER)
        foundAuthorization = session.call {
            authorizationService.update(foundAuthorization)
            authorizationService.read(personsId.toString()).get()
        }

        then: "the reread object has been updated"
        foundAuthorization.roles.contains(Role.USER)
        foundAuthorization.roles.size() == 1

        when: "the autorization is deleted"
        session.call {
            authorizationService.delete(authorization)
        }

        then: "it can't be found anymore"
        Optional<Authorization> optional = session.call {
            authorizationService.read(personsId.toString())
        }
        !optional.present

        cleanup:
        session.logout()
    }

    def "unauthorized user - user"() {
        setup:
        LoginSession session = LoginSession.loginAsUser()

        when:
        session.call {
            authorizationService.read("anything")
        }

        then:
        thrown EJBAccessException
    }

    def "unauthorized user - manager"() {
        setup:
        LoginSession session = LoginSession.loginAsManager()

        when:
        session.call {
            authorizationService.read("anything")
        }

        then:
        thrown EJBAccessException
    }

}