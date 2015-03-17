package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.entity.BaseEntity
import ch.haeuslers.bookr.entity.Person
import ch.haeuslers.bookr.entity.Role
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.inject.Inject

@RunWith(ArquillianSputnik.class)
class UserServiceSpec extends Specification {

    @Deployment
    def static JavaArchive "create deployment"() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClasses(PersonService.class, BaseEntity.class, Person.class, Role.class)
            .addAsResource("META-INF/persistence.xml");
    }

    @Inject
    PersonService service

    def setup() {
        assert service != null
    }

    def "minimum of #a and #b is #c"() {
        expect:
        Math.min(a, b) == c

        where:
        a | b || c
        3 | 7 || 3
        5 | 4 || 4
        9 | 9 || 9
    }

}
