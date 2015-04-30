package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.entity.Person
import ch.haeuslers.bookr.entity.Role
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.inject.Inject

@RunWith(ArquillianSputnik.class)
class RoleServiceSpec extends Specification {
    @Deployment
    def static WebArchive "create deployment"() {
        return ShrinkWrap.create(WebArchive.class, 'test.war')
            .addClass(RoleService.class)
            .addClass(PersonService.class)
            .addPackage(Role.class.getPackage())
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
    }

    @Inject
    RoleService roleService

    @Inject
    PersonService personService

    def "create person and add role to it"() {
        setup:
        Person person = new Person()
        person.principalName = "name"
        person = personService.create(person)

        when:
        Role role = roleService.addRoleToPerson(person.getId(), Role.Type.USER)
        List<Role> roles = roleService.findRolesForPerson(person.getId())

        then:
        role.person.equals(person)
        role.type.equals(Role.Type.USER)

        roles.contains(role)
        roles.size() == 1
    }
}
