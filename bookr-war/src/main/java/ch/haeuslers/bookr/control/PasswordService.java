package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.common.performance.PerformanceLogged;
import ch.haeuslers.bookr.entity.Authorization;
import ch.haeuslers.bookr.entity.Password;

import javax.annotation.Resource;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
@PerformanceLogged
public class PasswordService {

    @Inject
    private transient EntityManager em;

    @Resource
    private transient SessionContext context;

    public void create(final Password password) {
        ensureRights(password.getAuthorization());
        em.persist(password);
    }

    public void updatePassword(final Authorization authorization, final String password) {
        ensureRights(authorization);
        final Password existing = em.find(Password.class, authorization);
        // TODO fail when it's the same
        existing.setPassword(password);
    }

    private void ensureRights(final Authorization authorization) {
        if (!context.isCallerInRole("ADMINISTRATOR")
            || !context.getCallerPrincipal().getName().equals(authorization.getPrincipalName())) {
            throw new EJBAccessException("only for administrators or the user itself");
        }
    }
}
