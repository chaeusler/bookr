package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Project;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProjectService {
    @PersistenceContext(unitName = "bookr")
    EntityManager em;

    public Project find(String id) {
        return em.find(Project.class, id);
    }
}
