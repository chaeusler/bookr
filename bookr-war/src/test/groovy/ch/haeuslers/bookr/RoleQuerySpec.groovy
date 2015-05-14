package ch.haeuslers.bookr

import ch.haeuslers.bookr.control.EntityManagerProducer
import ch.haeuslers.bookr.entity.Person
import ch.haeuslers.bookr.entity.Authorization
import ch.haeuslers.bookr.entity.Role
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Query
import javax.transaction.UserTransaction

@RunWith(ArquillianSputnik.class)
class RoleQuerySpec extends Specification {

    @Deployment
    def static WebArchive "create deployment"() {
        ShrinkWrap.create(WebArchive.class, 'PersonServiceSpec.war')
            .addPackage(Person.class.getPackage())
            .addClass(EntityManagerProducer.class)
            .addClass(JBossLoginContextFactory.class)
            .addAsWebInfResource("META-INF/jboss-ejb3.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsResource("users.properties")
            .addAsResource("roles.properties")
    }

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    def "query for login module of jboss"() {

        setup:
        utx.begin()
        em.joinTransaction()

        Person person = new Person(principalName: "principal", id: UUID.randomUUID())
        Authorization personAuthorization = new Authorization(person: person, roles: [Role.USER, Role.MANAGER])

        em.persist(person)
        em.persist(personAuthorization)
        utx.commit()

        when: "the query happens"
        utx.begin()
        em.joinTransaction()
        Query q = em.createNativeQuery(
            "SELECT ar.role \"role\", 'Roles' " +
                "FROM PERSON p, AUTHORIZATION_ROLE ar " +
                "WHERE p.id = ar.authorization_id AND p.principalName = 'principal'")
        List results = q.getResultList();

        then:
        results.size() == 2

        cleanup:
        utx.commit()
    }
}