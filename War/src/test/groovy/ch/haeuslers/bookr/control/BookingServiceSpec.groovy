package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.entity.Booking
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
        return ShrinkWrap.create(WebArchive.class, 'test.war')
            .addClass(BookingService.class)
            .addClass(PersonService.class)
            .addPackage(Booking.class.getPackage())
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
    }

    @Inject
    BookingService bookingService


}
