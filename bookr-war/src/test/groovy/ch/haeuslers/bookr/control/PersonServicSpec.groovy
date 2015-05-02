package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.JBossLoginContextFactory
import ch.haeuslers.bookr.entity.Person
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.annotation.security.RunAs
import javax.ejb.EJBAccessException
import javax.inject.Inject
import javax.security.auth.Subject
import javax.security.auth.login.FailedLoginException
import javax.security.auth.login.LoginContext
import java.security.PrivilegedAction

@RunAs("ADMINISTRATOR")
@RunWith(ArquillianSputnik.class)
// Can't name it PersonServiceSpec. Intellij wouldn't recognize as Spec. Weird!
class PersonServicSpec extends Specification {

    @Deployment
    def static WebArchive "create deployment"() {
        ShrinkWrap.create(WebArchive.class, 'test.war')
            .addClass(PersonService.class)
            .addClass(PasswordService.class)
            .addClass(JBossLoginContextFactory.class)
            .addPackage(Person.class.getPackage())
            .addAsWebInfResource("META-INF/jboss-ejb3.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsResource("users.properties")
            .addAsResource("roles.properties")
    }

    @Inject
    PersonService service

    def setup() {
        assert service != null
    }

    def "invalid login"() {
        setup:
        LoginContext loginContext = JBossLoginContextFactory.createLoginContext('adminasdfistrator', 'admasdfinistrator')

        when:
        loginContext.login()

        then:
        thrown FailedLoginException
    }

    def "persist person and get it back as administrator"() {
        setup:
        Person person = new Person(principalName: 'theName')
        LoginContext loginContext = JBossLoginContextFactory.createLoginContext('administrator', 'administrator')
        loginContext.login()

        when:
        Person persisted = Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Person>() {
            @Override
            Person run() {
                service.create(person)
            }
        })

        then:
        service.getAll().contains(person)
        service.getAll().size() == 1
        service.getAll().get(0).equals(persisted)

        cleanup:
        loginContext.logout()
    }

    def "persist person and get it back - without admin role"() {
        setup:
        Person person = new Person(principalName: 'theName')
        LoginContext loginContext = JBossLoginContextFactory.createLoginContext('user', 'user')
        loginContext.login()

        when:
        Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Person>() {
            @Override
            Person run() {
                service.create(person)
            }
        })

        then:
        thrown EJBAccessException

        cleanup:
        loginContext.logout()
    }

}
