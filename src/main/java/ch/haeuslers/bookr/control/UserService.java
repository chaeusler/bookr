package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "bookr")
    EntityManager em;

    public Person getByPrincipalName(String principalName) {
        return em.createNamedQuery("User.findByPrincipalName", Person.class).setParameter("principalName", principalName).getSingleResult();
    }
}
