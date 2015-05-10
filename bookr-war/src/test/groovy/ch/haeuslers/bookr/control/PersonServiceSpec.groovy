package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.JBossLoginContextFactory
import ch.haeuslers.bookr.entity.Person
import ch.haeuslers.bookr.entity.UUIDToStringConverter
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.annotation.security.RunAs
import javax.ejb.EJBAccessException
import javax.inject.Inject
import javax.security.auth.login.FailedLoginException
import javax.security.auth.login.LoginContext

import static ch.haeuslers.bookr.control.SecurityUtils.*;

@RunAs("ADMINISTRATOR")
@RunWith(ArquillianSputnik.class)
class PersonServiceSpec extends Specification {

    @Deployment
    def static WebArchive "create deployment"() {
        ShrinkWrap.create(WebArchive.class, 'PersonServiceSpec.war')
            .addClass(PersonService.class)
            .addClass(PasswordService.class)
            .addClass(UUIDToStringConverter.class)
            .addPackage(Person.class.getPackage())
            .addClass(JBossLoginContextFactory.class)
            .addClass(SecurityUtils.class)
            .addClass(EntityManagerProducer.class)
            .addAsWebInfResource("META-INF/jboss-ejb3.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsResource("users.properties")
            .addAsResource("roles.properties")
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
        Person person = new Person(principalName: '1', id: UUID.randomUUID())
        LoginContext loginContext = loginAsAdministrator()

        when:
        doWith(loginContext) { service.create(person) }

        then:
        service.getAll().contains(person)
        service.getAll().size() == 1
        service.getAll().get(0).getPrincipalName().equals('1')

        cleanup:
        loginContext.logout()
    }

    def "create and get it back - without admin role - throws exception"() {
        setup:
        Person person = new Person(principalName: '2', id: UUID.randomUUID())
        LoginContext loginContext = loginAsUser()

        when:
        doWith(loginContext) { service.create(person) }

        then:
        thrown EJBAccessException

        cleanup:
        loginContext.logout()
    }

    def "create and delete it afterwards"() {
        setup:
        Person person = new Person(principalName: '3', id: UUID.randomUUID())
        LoginContext loginContext = loginAsAdministrator()

        when:
        doWith(loginContext) { service.create(person) }

        then:
        service.getAll().contains(person)

        when:
        doWith(loginContext) { service.delete(person) }

        then:
        !service.getAll().contains(person)

        cleanup:
        loginContext.logout()
    }

    def "create, find and update as administrator"() {
        setup:
        UUID id = UUID.randomUUID()
        Person person = new Person(principalName: '4', id: id)
        LoginContext loginContext = loginAsAdministrator()

        when: 'create person'
        doWith(loginContext) { service.create(person) }

        then: 'verify that the person was added'
        service.getAll().contains(person)

        when: 'find person by id'
        Optional<Person> foundPerson = service.find(id.toString())

        then: 'check equality of the found person'
        foundPerson.get().equals(person)

        when: 'update the principalName'
        foundPerson.get().principalName = 'new Name'
        def updated = doWith(loginContext) { service.update(foundPerson.get()) }

        then: 'verify the updated entity'
        updated.principalName.equals('new Name')

        cleanup:
        loginContext.logout()
    }
}
