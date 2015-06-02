package ch.haeuslers.bookr.person.service
import ch.haeuslers.bookr.core.JBossLoginContextFactory
import ch.haeuslers.bookr.core.LoginSession
import ch.haeuslers.bookr.person.api.Person
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.ejb.EJBAccessException
import javax.inject.Inject
import javax.security.auth.login.FailedLoginException
import javax.security.auth.login.LoginContext

@RunWith(ArquillianSputnik.class)
class PersonServiceSpec extends Specification {

    @Deployment
    def static WebArchive "create deployment"() {
        PersonDeployment.person()
    }

    @Inject
    PersonService service

    def "invalid login"() {
        setup:
        LoginContext loginContext = JBossLoginContextFactory.createLoginContext('adminasdfistrator', 'admasdfinistrator')

        when:
        loginContext.login()

        then:
        thrown FailedLoginException
    }

    def "create and get it back as administrator"() {
        setup:
        Person person = new Person(name: '1', id: UUID.randomUUID())
        LoginSession session = LoginSession.loginAsAdministrator()

        when:
        session.call { service.create(person) }

        then:
        service.getAll().contains(person)
        service.getAll().size() == 1
        service.getAll().get(0).getName().equals('1')

        cleanup:
        session.logout()
    }

    def "create and get it back - without admin role - throws exception"() {
        setup:
        Person person = new Person(name: '2', id: UUID.randomUUID())
        LoginSession session = LoginSession.loginAsUser()

        when:
        session.call { service.create(person) }

        then:
        thrown EJBAccessException

        cleanup:
        session.logout()
    }

    def "create and delete it afterwards"() {
        setup:
        Person person = new Person(name: '3', id: UUID.randomUUID())
        LoginSession session = LoginSession.loginAsAdministrator()

        when:
        session.call { service.create(person) }

        then:
        service.getAll().contains(person)

        when:
        session.call { service.delete(person) }

        then:
        !service.getAll().contains(person)

        cleanup:
        session.logout()
    }

    def "create, find and update as administrator"() {
        setup:
        UUID id = UUID.randomUUID()
        Person person = new Person(name: '4', id: id)
        LoginSession session = LoginSession.loginAsAdministrator()

        when: 'create person'
        session.call { service.create(person) }

        then: 'verify that the person was added'
        service.getAll().contains(person)

        when: 'find person by id'
        Optional<Person> foundPerson = service.read(id.toString())

        then: 'check equality of the found person'
        foundPerson.get().equals(person)

        when: 'update the principalName'
        foundPerson.get().name = 'new Name'
        def updated = session.call { service.update(foundPerson.get()) }

        then: 'verify the updated entity'
        updated.name.equals('new Name')

        cleanup:
        session.logout()
    }
}
