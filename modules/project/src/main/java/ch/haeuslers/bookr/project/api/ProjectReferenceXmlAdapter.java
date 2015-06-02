package ch.haeuslers.bookr.project.api;

import ch.haeuslers.bookr.core.api.EntityXmlAdapter;

import javax.persistence.EntityManager;
import java.util.Optional;

public class ProjectReferenceXmlAdapter extends EntityXmlAdapter<String, Project> {

    @Override
    public Project unmarshal(String projectId) throws Exception {
        EntityManager em = getEntityManager();

        return Optional.ofNullable(em.find(Project.class, projectId))
            .orElseThrow(() -> new RuntimeException("unable to unmarshal project.id - entity not found"));
    }

    @Override
    public String marshal(Project project) throws Exception {
        return project.getId();
    }
}
