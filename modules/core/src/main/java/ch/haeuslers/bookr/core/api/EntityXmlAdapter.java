package ch.haeuslers.bookr.core.api;

import ch.haeuslers.bookr.core.common.BeanLocator;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public abstract class EntityXmlAdapter<VALUETYPE, BOUNDTYPE> extends XmlAdapter<VALUETYPE, BOUNDTYPE> {

    public static final String JNDI_NAME = "java:/entitymanager/bookr";

    private EntityManager em;

    protected EntityManager getEntityManager() {
        if (em == null) {
            em = (EntityManager) BeanLocator.lookup(JNDI_NAME);
            if (em == null) {
                throw new RuntimeException("unable to lookup for entity manager named: " + JNDI_NAME);
            }
        }

        return em;
    }
}
