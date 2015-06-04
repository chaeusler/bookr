package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Project;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Stateless
@DeclareRoles({"MANAGER", "ADMINISTRATOR"})
@RolesAllowed({"MANAGER", "ADMINISTRATOR"})
public class ProjectService {

    @Inject
    private transient EntityManager em;

    public void create(final Project project) {
        em.persist(project);
    }

    public Optional<Project> read(final String id) {
        return Optional.ofNullable(em.find(Project.class, id));
    }

    public void update(final Project project) {
        em.merge(project);
    }

    public void delete(final String id) {
        read(id).ifPresent(em::remove);
    }

    public List<Project> getAll() {
        return em.createNamedQuery(Project.QUERY_ALL, Project.class).getResultList();
    }
}
