package ch.haeuslers.bookr

import ch.haeuslers.bookr.common.EntityManagerProducer
import ch.haeuslers.bookr.entity.Password
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

        Person person = new Person(name: "principal", id: UUID.randomUUID())
        Authorization personAuthorization = new Authorization(person: person, principalName: "principal", roles: [Role.USER, Role.MANAGER])
        Password password = new Password(id: person.id, authorization: personAuthorization, password: "top secret")

        em.persist(person)
        em.persist(personAuthorization)
        em.persist(password)
        utx.commit()


        when: "password query"
        Query q1 = em.createNativeQuery(
            "SELECT p.password " +
                "FROM BOOKR_AUTHORIZATION a JOIN BOOKR_PASSWORD p " +
                "ON a.person_id = p.authorization_id " +
                "WHERE a.principalName = 'principal'")
        List results1 = q1.getResultList();

        then:
        results1.size() == 1

        when: "role query"
        Query q2 = em.createNativeQuery(
            "SELECT ar.role, 'Roles' " +
                "FROM BOOKR_AUTHORIZATION a JOIN BOOKR_AUTHORIZATION_ROLE ar " +
                "ON a.person_id = ar.authorization_id " +
                "WHERE a.principalName = 'principal'")
        List results2 = q2.getResultList();

        then:
        results2.size() == 2
    }
}