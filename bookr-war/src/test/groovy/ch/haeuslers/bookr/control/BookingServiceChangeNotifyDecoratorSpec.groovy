package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.JBossLoginContextFactory
import ch.haeuslers.bookr.common.EntityManagerProducer
import ch.haeuslers.bookr.common.performance.PerformanceLogger
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
import javax.persistence.EntityManager
import javax.transaction.UserTransaction
import java.time.LocalDateTime


@RunWith(ArquillianSputnik.class)
class BookingServiceChangeNotifyDecoratorSpec extends Specification {

    @Deployment
    def static WebArchive "create deployment"() {
        return ShrinkWrap.create(WebArchive.class, 'BookingServiceChangeNotifyDecoratorSpec.war')
            .addPackage(BookingService.class.getPackage())
            .addPackage(Booking.class.getPackage())
            .addPackage(PerformanceLogger.class.getPackage())
            .addPackage(EntityManagerProducer.class.getPackage())
            .addClass(BookingChangeEventReceiver.class)
            .addClass(JBossLoginContextFactory.class)
            .addClass(LoginSession.class)
            .addAsWebInfResource("META-INF/jboss-ejb3.xml")
            .addAsResource("META-INF/beans.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsResource("users.properties")
            .addAsResource("roles.properties")
    }

    @Inject
    transient BookingService bookingService

    @Inject
    transient EntityManager em

    @Inject
    transient UserTransaction utx;

    @Inject
    transient BookingChangeEventReceiver receiver;

    Person theUser
    Person anotherPerson
    Project theProject

    def setup() {
        theUser = new Person(id: UUID.randomUUID().toString(), name: 'user 1')
        theProject = new Project(id: UUID.randomUUID().toString(), name: 'the name 1', persons: [theUser])
        anotherPerson = new Person(id: UUID.randomUUID().toString(), name: 'another user 1')

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

    def "create booking and receive notification"() {
        setup:
        LoginSession session = LoginSession.loginAsAdministrator();

        when: "creating a new booking"
        String id = UUID.randomUUID().toString()
        Booking booking = new Booking(
            id: id,
            project: theProject,
            person: theUser,
            startTime: LocalDateTime.now().minusHours(1),
            endTime: LocalDateTime.now().plusHours(1),
            description: 'lets test'
        )
        session.call {
            bookingService.create(booking)
        }

        then: "we shoould have reveiced the create event"
        receiver.bookingChangeEvent.entityId == id
        receiver.bookingChangeEvent.entityType == Booking.class
        receiver.bookingChangeEvent.changeType == ChangeEvent.ChangeType.CREATE;

        cleanup:
        session.call {
            bookingService.delete(id)
        }
        session.logout()
    }
}