package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.JBossLoginContextFactory
import ch.haeuslers.bookr.entity.Booking
import ch.haeuslers.bookr.entity.Person
import ch.haeuslers.bookr.entity.Project
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.inject.Inject


@RunWith(ArquillianSputnik.class)
class BookingServiceSpec extends Specification {

    @Deployment
    def static WebArchive "create deployment"() {
        return ShrinkWrap.create(WebArchive.class, 'BookingServiceSpec.war')
            .addClass(BookingService.class)
            .addClass(ProjectService.class)
            .addClass(PersonService.class)
            .addClass(PasswordService.class)
            .addPackage(Booking.class.getPackage())
            .addClass(JBossLoginContextFactory.class)
            .addClass(LoginSession.class)
            .addClass(EntityManagerProducer.class)
            .addAsWebInfResource("META-INF/jboss-ejb3.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsResource("users.properties")
            .addAsResource("roles.properties")
    }

    @Inject
    BookingService bookingService

    @Inject
    ProjectService projectService

    @Inject
    PersonService personService

    Person theUser
    Project theProject

    def setup() {
        LoginSession session = LoginSession.loginAsAdministrator();

        theUser = new Person(id: UUID.randomUUID(), principalName: 'the name')
        theProject = new Project(id: UUID.randomUUID(), name: 'the name', persons: [theUser])

        session.call {
            personService.create(theUser)
            projectService.create(theProject)
        }

        session.logout()
    }

    def "crud as admin"() {
        setup:
        LoginSession session = LoginSession.loginAsAdministrator();

        when: "creating a new booking"
        Booking booking = new Booking(
            id: UUID.randomUUID(),
            project: theProject,
            person: theUser,
            start: new Date(System.currentTimeMillis() - 100000),
            end: new Date(),
            description: 'lets test'
        )
        session.call {
            bookingService.create(booking)
        }

        then: "we should be able to get it"
        Booking foundBooking = session.call {
            bookingService.read(booking.id.toString()).get()
        }
        foundBooking.equals(booking)

        cleanup:
        session.logout()
    }
}