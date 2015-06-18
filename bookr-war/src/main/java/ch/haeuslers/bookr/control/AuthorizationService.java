package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.common.Audited;
import ch.haeuslers.bookr.common.performance.PerformanceLogged;
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
@PerformanceLogged
@Audited
public class AuthorizationService implements CRUDService<Authorization> {

    @Inject
    private transient EntityManager em;

    public void create(final Authorization authorization) {
        authorization.getRoles().add(Role.USER);
        em.persist(authorization);
    }

    public List<Authorization> readAll() {
        return em.createNamedQuery(Authorization.QUERY_ALL, Authorization.class).getResultList();
    }

    public Optional<Authorization> read(final String personId) {
        return Optional.ofNullable(em.find(Authorization.class, personId));
    }

    public Authorization update(final Authorization authorization) {
        authorization.getRoles().add(Role.USER);
        return em.merge(authorization);
    }

    public void delete(final String authorizationId) {
        read(authorizationId).ifPresent(em::remove);
    }
}
