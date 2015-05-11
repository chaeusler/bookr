package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Project;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Stateless
public class ProjectService {
    @Inject
    EntityManager em;

    public Project find(String id) {
        return em.find(Project.class, id);
    }
}
