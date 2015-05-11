package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.PersonAuthorization;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

@Stateless
@DeclareRoles("ADMINISTRATOR")
@RolesAllowed("ADMINISTRATOR")
public class PersonAuthorizationService {

    @Inject
    EntityManager em;

    public void create(PersonAuthorization personAuthorization) {
        em.persist(personAuthorization);
    }

    public Optional<PersonAuthorization> read(String personId) {
        return Optional.ofNullable(em.find(PersonAuthorization.class, personId));
    }

    public void update(PersonAuthorization personAuthorization) {
        em.merge(personAuthorization);
    }

    public void delete(PersonAuthorization personAuthorization) {
        personAuthorization = em.merge(personAuthorization);
        em.remove(personAuthorization);
    }
}
