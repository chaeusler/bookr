package ch.haeuslers.bookr.core

import ch.haeuslers.bookr.core.api.EntityXmlAdapter
import ch.haeuslers.bookr.core.common.PerformanceLogger
import org.jboss.shrinkwrap.api.Archive
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.StringAsset
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.jboss.shrinkwrap.descriptor.api.Descriptors
import org.jboss.shrinkwrap.descriptor.api.beans11.BeansDescriptor
import org.jboss.shrinkwrap.descriptor.api.persistence21.PersistenceDescriptor

/**
 * Contains deployment for arquillian tests.
 */
class CoreDeployment {

    def static Archive core() {
        ShrinkWrap.create(JavaArchive.class, 'BasicBookr.jar')
            .addAsManifestResource(new StringAsset(persistenceDescriptor().exportAsString()), 'persistence.xml')
            .addPackage('ch.haeuslers.bookr.core.api')
            .addPackage('ch.haeuslers.bookr.core.common')
            .addAsResource(new StringAsset(usersProperties()), "users.properties")
            .addAsResource(new StringAsset(rolesProperties()), "roles.properties")
    }

    def private static PersistenceDescriptor persistenceDescriptor() {
        Descriptors.create(PersistenceDescriptor.class)
            .createPersistenceUnit()
            .name("test")
            .getOrCreateProperties()
            .createProperty()
            .name("hibernate.hbm2ddl.auto")
            .value("create-drop").up()
            .createProperty()
            .name("hibernate.show_sql")
            .value("true").up().up()
            .jtaDataSource("java:jboss/datasources/ExampleDS").up();
    }

    def static BeansDescriptor beansDescriptor() {
        Descriptors.create(BeansDescriptor.class)
            .getOrCreateInterceptors()
            .clazz(PerformanceLogger.class.name)
            .up()
    }

    private static String usersProperties() {
        "user=user\n" +
            "administrator=administrator\n" +
            "manager=manager"
    }

    private static String rolesProperties() {
        "user=USER\n" +
            "manager=MANAGER,USER\n" +
            "administrator=ADMINISTRATOR,USER"
    }
}
