package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.JBossLoginContextFactory
import ch.haeuslers.bookr.entity.Person
import ch.haeuslers.bookr.entity.Project
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.security.auth.login.LoginContext

import static ch.haeuslers.bookr.control.SecurityUtils.*;

import javax.inject.Inject

@RunWith(ArquillianSputnik.class)
class ProjectServiceSpec extends Specification {

    @Deployment
    def static WebArchive "create deployment"() {
        return ShrinkWrap.create(WebArchive.class, 'ProjectServiceSpec.war')
            .addClass(ProjectService.class)
            .addClass(PersonService.class)
            .addClass(PasswordService.class)
            .addPackage(Project.class.getPackage())
            .addClass(JBossLoginContextFactory.class)
            .addClass(SecurityUtils.class)
            .addClass(EntityManagerProducer.class)
            .addAsWebInfResource("META-INF/jboss-ejb3.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsResource("users.properties")
            .addAsResource("roles.properties")
    }

    @Inject
    ProjectService projectService

    @Inject
    PersonService personService

    def "crud as admin" (){
        setup:
        LoginContext loginContext = loginAsAdministrator()

        when: "create project"
        Project project = new Project(id:  UUID.randomUUID(), name: 'awesome project')
        doWith(loginContext) {
            projectService.create(project)
        }

        then: "the authorization can be found with the users id"
        Project foundProject = doWith(loginContext) {
            projectService.read(project.id.toString()).get()
        }
        foundProject.equals(project)

        when: "the persons are added is changed"
        Person person = new Person(id: UUID.randomUUID(), principalName: 'tho first')
        doWith(loginContext) {
            personService.create(person)
        }

        project.persons.add(person)

        foundProject = doWith(loginContext) {
            projectService.update(project)
            projectService.read(project.id.toString()).get()
        }

        then: "the reread object has been updated"
        foundProject.persons.contains(person)
        foundProject.persons.size() == 1

        when: "the autorization is deleted"
        doWith(loginContext) {
            projectService.delete(foundProject)
        }

        then: "it can't be found anymore"
        Optional<Project> optional = doWith(loginContext) {
            projectService.read(project.id.toString())
        }
        !optional.present

        cleanup:
        loginContext.logout()

    }
}

