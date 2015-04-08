package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Stateless
public class PersonService {

    @PersistenceContext(unitName = "bookr")
    EntityManager em;

    public Person persist(Person person) {
        if (person.getId() == null || person.getId().isEmpty()) {
            person.setId(UUID.randomUUID().toString());
        }
        em.persist(person);
        return person;
    }

    public Person find(String id) {
        return em.find(Person.class, id);
    }

    public List<Person> getAll() {
        return em.createNamedQuery(Person.QUERY_ALL, Person.class).getResultList();
    }

    public Person getByPrincipalName(String principalName) {
        return em.createNamedQuery(Person.QUERY_FIND_BY_PRINCIPAL_NAME, Person.class).setParameter("principalName", principalName).getSingleResult();
    }

    // TODO when deleting a person the roles need to be deleted too
}
