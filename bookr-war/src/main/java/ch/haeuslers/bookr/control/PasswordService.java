package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Authorization;
import ch.haeuslers.bookr.entity.Password;
import ch.haeuslers.bookr.entity.Person;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.context.Initialized;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PasswordService {

    @Inject
    EntityManager em;

    @Resource
    SessionContext context;

    public void create(Password password) {
        ensureRights(password.getAuthorization());
        em.persist(password);
    }

    public void updatePassword(Authorization authorization, String password) {
        ensureRights(authorization);
        Password existing = em.find(Password.class, authorization);
        // TODO fail when it's the same
        existing.setPassword(password);
    }

    private void ensureRights(Authorization authorization) {
        if (!context.isCallerInRole("ADMINISTRATOR")
            || !context.getCallerPrincipal().getName().equals(authorization.getPrincipalName())) {
            throw new EJBAccessException("only for administrators or the user itself");
        }
    }
}
