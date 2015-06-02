package ch.haeuslers.bookr.core

import org.jboss.shrinkwrap.api.Archive
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.JavaArchive

/**
 * Contains deployment for arquillian tests.
 */
class CoreDeployment {
    def static Archive core() {
        ShrinkWrap.create(JavaArchive.class, 'BasicBookr.jar')
            .addPackage('ch.haeuslers.bookr.core.api')
            .addPackage('ch.haeuslers.bookr.core.common')
            .addAsResource("users.properties")
            .addAsResource("roles.properties")
    }
}
