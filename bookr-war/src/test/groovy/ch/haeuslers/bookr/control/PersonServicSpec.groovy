package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.entity.Person
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.inject.Inject

@RunWith(ArquillianSputnik.class)
// Can't name it PersonServiceSpec. Intellij wouldn't recognize as Spec. Weird!
class PersonServicSpec extends Specification {

    @Deployment
    def static WebArchive "create deployment"() {
        return ShrinkWrap.create(WebArchive.class, 'test.war')
            .addClass(PersonService.class)
            .addPackage(Person.class.getPackage())
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
    }

    @Inject
    PersonService service

    def setup() {
        assert service != null
    }

    def "persist and get"() {
        setup:
        Person person = new Person()
        person.principalName = "theName"

        when:
        def persisted = service.create(person)

        then:
        service.getAll().contains(person)
        service.getAll().size() == 1
        service.getAll().get(0).equals(persisted)
    }

}
