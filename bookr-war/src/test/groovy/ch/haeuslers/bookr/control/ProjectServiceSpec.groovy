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

import javax.ejb.EJBException
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
            .addClass(LoginSession.class)
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
        LoginSession session = LoginSession.loginAsAdministrator();

        when: "create project"
        Project project = new Project(id:  UUID.randomUUID(), name: 'awesome project')
        session.call {
            projectService.create(project)
        }

        then: "the authorization can be found with the users id"
        Project foundProject = session.call {
            projectService.read(project.id.toString()).get()
        }
        foundProject.equals(project)

        when: "the persons are added"
        Person person = new Person(id: UUID.randomUUID(), name: 'the first')
        session.call {
            personService.create(person)
        }

        project.persons.add(person)

        foundProject = session.call {
            projectService.update(project)
            projectService.read(project.id.toString()).get()
        }

        then: "the reread object has been updated"
        foundProject.persons.contains(person)
        foundProject.persons.size() == 1

        when: "the autorization is deleted"
        session.call {
            projectService.delete(foundProject.id)
        }

        then: "it can't be found anymore"
        Optional<Project> optional = session.call {
            projectService.read(project.id.toString())
        }
        !optional.present

        cleanup:
        session.logout()
    }

    def "create as user fails"() {
        setup:
        LoginSession session = LoginSession.loginAsUser()

        when:
        session.call {
            projectService.create(new Project(id: UUID.randomUUID(), name: "noname"))
        }

        then:
        thrown EJBException
    }
}

