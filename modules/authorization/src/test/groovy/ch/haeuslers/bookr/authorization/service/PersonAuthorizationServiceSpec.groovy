package ch.haeuslers.bookr.authorization.service

import ch.haeuslers.bookr.authorization.api.Authorization
import ch.haeuslers.bookr.core.CoreDeployment
import ch.haeuslers.bookr.core.JBossLoginContextFactory
import ch.haeuslers.bookr.core.LoginSession
import ch.haeuslers.bookr.core.common.EntityManagerProducer
import ch.haeuslers.bookr.core.common.Role
import ch.haeuslers.bookr.person.api.Person
import ch.haeuslers.bookr.person.api.PersonService
import ch.haeuslers.bookr.person.service.PersonDeployment
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
            .addClass(AuthorizationService.class)
            .addClass(PasswordService.class)
            .addAsLibrary(PersonDeployment.personJar())
            .addAsLibrary(CoreDeployment.core())
            .addAsWebInfResource("META-INF/jboss-ejb3.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsResource("users.properties")
            .addAsResource("roles.properties")
    }

    @Inject
    PersonService personService

    @Inject
    AuthorizationService authorizationService

    def "read and update as admin"() {
        setup:
        LoginSession session = LoginSession.loginAsAdministrator()
        Person user = new Person(id: UUID.randomUUID().toString(), name: 'wertwertwert')
        Authorization authorization = new Authorization(person: user, principalName: 'principalname')

        when: "create user and authorization"
        Authorization foundAuthorization = session.call {
            personService.create(user)
            authorizationService.create(authorization)
            authorizationService.read(user.id).get()
        }

        then: "ith should be there"
        foundAuthorization.roles.size() == 1
        foundAuthorization.roles.contains(Role.USER)

        when: "the authorization is changed"
        foundAuthorization.roles.add(Role.MANAGER)
        foundAuthorization = session.call {
            authorizationService.update(foundAuthorization)
            authorizationService.read(user.id).get()
        }

        then: "the reread object has been updated"
        foundAuthorization.roles.contains(Role.MANAGER)
        foundAuthorization.roles.size() == 2

        when: "the user is deleted"
        Optional<Authorization> optional = session.call {
            authorizationService.delete(user.id)
            personService.delete(user)
            authorizationService.read(user.id)
        }

        then: "it can't be found anymore"
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