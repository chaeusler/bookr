package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Project;
import ch.haeuslers.bookr.entity.RoleType;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;

@Stateless
@DeclareRoles({"MANAGER", "ADMINISTRATOR"})
@RolesAllowed({"MANAGER", "ADMINISTRATOR"})
public class ProjectService {

    @Inject
    EntityManager em;
    
    public void create(Project project) {
        em.persist(project);
    }

    public Optional<Project> read(String id) {
        return Optional.ofNullable(em.find(Project.class, id));
    }

    public void update(Project project) {
        em.merge(project);
    }

    public void delete(Project project) {
        project = em.merge(project);
        em.remove(project);
    }
}
