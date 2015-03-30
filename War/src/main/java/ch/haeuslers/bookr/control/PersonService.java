package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import java.util.List;

@Stateless
@Path("/persons")
@Consumes("application/json")
@Produces("application/json")
public class PersonService {

    @PersistenceContext(unitName = "bookr")
    EntityManager em;


    @PUT
    public void create(Person  person) {
        em.persist(person);
        // TODO return URL to the resource
    }

    @GET
    public List<Person> getAll() {
        return em.createNamedQuery(Person.QUERY_ALL, Person.class).getResultList();
    }

    public Person getByPrincipalName(String principalName) {
        return em.createNamedQuery(Person.QUERY_FIND_BY_PRINCIPAL_NAME, Person.class).setParameter("principalName", principalName).getSingleResult();
    }
}
