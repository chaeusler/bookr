package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.entity.Person
import ch.haeuslers.bookr.entity.Project
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

@RunWith(ArquillianSputnik.class)
class ProjectServiceSpec extends Specification {

    @Deployment
    def static WebArchive "create deployment"() {
        return ShrinkWrap.create(WebArchive.class, 'test.war')
            .addClass(ProjectService.class)
            .addPackage(Project.class.getPackage())
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
    }
}