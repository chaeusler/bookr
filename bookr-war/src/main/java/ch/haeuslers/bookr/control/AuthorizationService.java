package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Authorization;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

@Stateless
@DeclareRoles("ADMINISTRATOR")
@RolesAllowed("ADMINISTRATOR")
public class AuthorizationService {

    @Inject
    EntityManager em;

    public void create(Authorization authorization) {
        em.persist(authorization);
    }

    public Optional<Authorization> read(String personId) {
        return Optional.ofNullable(em.find(Authorization.class, personId));
    }

    public void update(Authorization authorization) {
        em.merge(authorization);
    }

    public void delete(Authorization authorization) {
        authorization = em.merge(authorization);
        em.remove(authorization);
    }
}
