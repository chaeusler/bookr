package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Password;
import ch.haeuslers.bookr.entity.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PasswordService {

    @PersistenceContext(unitName = "bookr")
    EntityManager em;

    public void updatePassword(Person person, String password) {
        Password existing = em.find(Password.class, person);
        // TODO fail when it's the same
        existing.setPassword(password);
    }
}
