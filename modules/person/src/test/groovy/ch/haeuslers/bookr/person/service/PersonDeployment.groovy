package ch.haeuslers.bookr.person.service

import ch.haeuslers.bookr.core.CoreDeployment
import ch.haeuslers.bookr.person.api.Person
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
/**
 * Contains module deployments for test with arquillian.
 */
class PersonDeployment {
    def static person() {
        ShrinkWrap.create(WebArchive.class, 'Person.war')
            .addAsLibrary(CoreDeployment.core())
            .addClass(Person.class)
            .addClass(PersonService.class)
            .addAsResource("META-INF/beans.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
    }
}
