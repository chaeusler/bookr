package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.JBossLoginContextFactory
import ch.haeuslers.bookr.entity.Booking
import ch.haeuslers.bookr.entity.LocalDateTimeConverter
import ch.haeuslers.bookr.entity.Password
import ch.haeuslers.bookr.entity.Person
import ch.haeuslers.bookr.entity.Project
import ch.haeuslers.bookr.entity.UUIDValidator
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.ejb.EJBException
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.UserTransaction
import java.security.PrivilegedActionException
import java.time.LocalDateTime


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
            .addClass(LocalDateTimeConverter.class)
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

    @Inject
    EntityManager em

    @Inject
    UserTransaction utx;

    Person theUser
    Person anotherPerson
    Project theProject

    def setup() {
        theUser = new Person(id: UUID.randomUUID().toString(), principalName: 'user')
        theProject = new Project(id: UUID.randomUUID().toString(), name: 'the name', persons: [theUser])
        anotherPerson = new Person(id: UUID.randomUUID().toString(), principalName: 'another user')

        utx.begin()
        em.joinTransaction()
        em.persist(theUser)
        em.persist(anotherPerson)
        em.persist(theProject)
        utx.commit()
    }

    def cleanup() {
        utx.begin()
        em.joinTransaction()
        em.remove(em.merge(theProject))
        em.remove(em.merge(anotherPerson))
        em.remove(em.merge(theUser))
        utx.commit()
    }

    def "crud as admin"() {
        setup:
        LoginSession session = LoginSession.loginAsAdministrator();

        when: "creating a new booking"
        Booking booking = new Booking(
            id: UUID.randomUUID().toString(),
            project: theProject,
            person: theUser,
            start: LocalDateTime.now().minusHours(1),
            end: LocalDateTime.now().plusHours(1),
            description: 'lets test'
        )
        session.call {
            bookingService.create(booking)
        }

        then: "we should be able to get it"
        Booking foundBooking = session.call {
            bookingService.read(booking.id).get()
        }
        foundBooking.equals(booking)

        when: "updating the booking"
        LocalDateTime endDate = foundBooking.end
        LocalDateTime newEndDate = endDate.plusHours(2);
        foundBooking.end = newEndDate
        foundBooking = session.call {
            bookingService.update(foundBooking)
            bookingService.read(foundBooking.id).get()
        }

        then: "the time should be updated"
        foundBooking.end.equals(newEndDate)

        when: "we delete the booking"
        Optional<Booking> deleted = session.call {
            bookingService.delete(foundBooking)
            bookingService.read(foundBooking.id)
        }

        then: "it's deleted"
        !deleted.isPresent()

        cleanup:
        session.logout()
    }

    def "create as wrong user fails"() {
        setup:
        LoginSession session = LoginSession.loginAsUser()

        when:

        Booking booking = new Booking(
            id: UUID.randomUUID().toString(),
            project: theProject,
            person: anotherPerson,
            start: LocalDateTime.now().minusHours(1),
            end: LocalDateTime.now().plusHours(1),
            description: 'lets test'
        )
        session.call {
            bookingService.create(booking)
        }

        then:
        thrown PrivilegedActionException
    }

    def "create as right user successes"() {
        setup:
        LoginSession session = LoginSession.loginAsUser()

        when:
        Booking booking = new Booking(
            id: UUID.randomUUID().toString(),
            project: theProject,
            person: theUser,
            start: LocalDateTime.now().minusHours(1),
            end: LocalDateTime.now().plusHours(1),
            description: 'lets test'
        )
        Booking foundBooking = session.call {
            bookingService.create(booking)
            bookingService.read(booking.id).get()
        }

        then:
        foundBooking.equals(booking)

        when: "we delete the booking"
        Optional<Booking> deleted = session.call {
            bookingService.delete(foundBooking)
            bookingService.read(foundBooking.id)
        }

        then: "it's deleted"
        !deleted.isPresent()
    }
}