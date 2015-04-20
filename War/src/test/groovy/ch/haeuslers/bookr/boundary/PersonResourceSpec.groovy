package ch.haeuslers.bookr.boundary

import ch.haeuslers.bookr.control.PersonService
import ch.haeuslers.bookr.entity.Person
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.arquillian.test.api.ArquillianResource
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification
import spock.lang.Stepwise

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget

@Stepwise
@RunWith(ArquillianSputnik.class)
class PersonResourceSpec extends Specification {

    private WebTarget target;

    @Deployment(testable = false)
    def static WebArchive "create deployment"() {
        return ShrinkWrap.create(WebArchive.class)
            .addClass(RestApplication.class)
            .addClass(PersonResource.class)
            .addClass(PersonService.class)
            .addClass(Person.class)
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
    }

    @ArquillianResource
    private URL base;

    def setup() {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "rest/v1/persons").toExternalForm()));
    }

    def "no users"() {
        expect:
        target.request().get(List.class).isEmpty();
    }
}
