package ch.haeuslers.bookr.person.service

import ch.haeuslers.bookr.core.CoreDeployment
import ch.haeuslers.bookr.person.api.Person
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.jboss.shrinkwrap.api.spec.WebArchive

/**
 * Contains module deployments for test with arquillian.
 */
class PersonDeployment {
    def static JavaArchive personJar() {
        ShrinkWrap.create(JavaArchive.class, 'person.jar')
            .addClass(Person.class)
            .addClass(PersonServiceBean.class)
            .addAsResource("META-INF/beans.xml")
    }

    def static WebArchive personWar() {
        ShrinkWrap.create(WebArchive.class, 'person.war')
            .merge(CoreDeployment.core())
            .merge(personJar())
    }
}
