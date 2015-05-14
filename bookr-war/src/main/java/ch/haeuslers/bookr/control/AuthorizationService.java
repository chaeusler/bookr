package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Authorization;
import ch.haeuslers.bookr.entity.Role;

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


    public Optional<Authorization> read(String personId) {
        return Optional.ofNullable(em.find(Authorization.class, personId));
    }

    public void update(Authorization authorization) {
        authorization.getRoles().add(Role.USER);
        em.merge(authorization);
    }
}
