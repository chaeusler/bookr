package ch.haeuslers.bookr.project.service;


import ch.haeuslers.bookr.project.api.Project;

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

    public void delete(String id) {
        read(id).ifPresent(em::remove);
    }

    public List<Project> getAll() {
        return em.createNamedQuery(Project.QUERY_ALL, Project.class).getResultList();
    }
}
