package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.JBossLoginContextFactory
import ch.haeuslers.bookr.entity.Person
import ch.haeuslers.bookr.entity.Role
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.inject.Inject
import javax.security.auth.login.LoginContext

import static ch.haeuslers.bookr.control.SecurityUtils.getDoWith;

@RunWith(ArquillianSputnik.class)
class RoleServiceSpec extends Specification {
    @Deployment
    def static WebArchive "create deployment"() {
        return ShrinkWrap.create(WebArchive.class, 'test.war')
            .addClass(RoleService.class)
            .addClass(PersonService.class)
            .addClass(PasswordService.class)
            .addPackage(Role.class.getPackage())
            .addClass(JBossLoginContextFactory.class)
            .addClass(SecurityUtils.class)
            .addAsWebInfResource("META-INF/jboss-ejb3.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsResource("users.properties")
            .addAsResource("roles.properties")
    }

    @Inject
    RoleService roleService

    @Inject
    PersonService personService

    def "create person and add role to it"() {
        setup:
        LoginContext loginContext = JBossLoginContextFactory.createLoginContext('administrator', 'administrator')
        loginContext.login()

        Person person = new Person(principalName: 'RoleServiceUser1', id: UUID.randomUUID().toString())

        when:
        def roles = doWith(loginContext) {
            personService.create(person)
            roleService.addRoleToPerson(person.getId(), Role.Type.USER)
            roleService.findRolesForPerson(person.getId())
        }


        then:
        roles.size() == 1
        def role = roles.findAll { it.person.equals(person) }.first()
        role.type.equals(Role.Type.USER)

        cleanup:
        loginContext.logout()
    }
}
