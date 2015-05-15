package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Authorization;
import ch.haeuslers.bookr.entity.Role;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Stateless
@DeclareRoles("ADMINISTRATOR")
@RolesAllowed("ADMINISTRATOR")
public class AuthorizationService {

    @Inject
    EntityManager em;

    public void create(Authorization authorization) {
        authorization.getRoles().add(Role.USER);
        em.persist(authorization);
    }

    public List<Authorization> readAll() {
        return em.createNamedQuery(Authorization.QUERY_ALL, Authorization.class).getResultList();
    }

    public Optional<Authorization> read(String personId) {
        return Optional.ofNullable(em.find(Authorization.class, personId));
    }

    public void update(Authorization authorization) {
        authorization.getRoles().add(Role.USER);
        em.merge(authorization);
    }

    public void delete(String authorizationId) {
        read(authorizationId).ifPresent(em::remove);
    }
}
