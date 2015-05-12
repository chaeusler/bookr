package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Password;
import ch.haeuslers.bookr.entity.Person;

import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateless;
import javax.enterprise.context.Initialized;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@DeclareRoles("")
public class PasswordService {

    @Inject
    EntityManager em;

    public void create(Password password) {
        em.persist(password);
    }

    public void updatePassword(Person person, String password) {
        Password existing = em.find(Password.class, person);
        // TODO fail when it's the same
        existing.setPassword(password);
    }
}
