package ch.haeuslers.bookr.core.common;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceContext(unitName = "bookr")
    private EntityManager em;

    @Default
    @Produces
    public EntityManager getEntityManager() {
        return em;
    }
}
