package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.common.Audited;
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
@Audited
public class PasswordService {

    @Inject
    private transient EntityManager em;

    @Resource
    private transient SessionContext context;

    public void create(final Password password) {
        ensureRights(password);
        em.persist(password);
    }

    public void updatePassword(final Password password) {
        ensureRights(password);
        // TODO fail when it's the same
        em.merge(password);
    }

    private void ensureRights(final Password password) {
        Authorization authorization = password.getAuthorization();
        String pName = authorization.getPrincipalName();
        if (!context.isCallerInRole("ADMINISTRATOR")
            && !context.getCallerPrincipal().getName().equals(pName)) {
            throw new EJBAccessException("only for administrators or the user itself");
        }
    }
}
