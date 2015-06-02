package ch.haeuslers.bookr.authorization.service;

import ch.haeuslers.bookr.authorization.api.Authorization;
import ch.haeuslers.bookr.authorization.api.Password;

import javax.annotation.Resource;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
