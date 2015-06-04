package ch.haeuslers.bookr.control;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceContext(unitName = "bookr")
    private transient EntityManager em;

    @Default
    @Produces
    public EntityManager getEntityManager() {
        return em;
    }
}
