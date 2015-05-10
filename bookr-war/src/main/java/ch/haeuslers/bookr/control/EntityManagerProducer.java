package ch.haeuslers.bookr.control;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceContext(unitName = "bookr")
    private EntityManager em;

    @Produces
    public EntityManager getEntityManager() {
        return em;
    }
}
