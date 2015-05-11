package ch.haeuslers.bookr.entity;

import ch.haeuslers.bookr.control.ProjectService;

import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Optional;

public class ProjectReferenceXmlAdapter extends XmlAdapter<String, Project> {

    @Inject
    ProjectService projectService;

    @Override
    public Project unmarshal(String projectId) throws Exception {
        return projectService
            .read(projectId)
            .orElseThrow(() -> new RuntimeException("unable to unmarshal project.id - entity not found"));
    }

    @Override
    public String marshal(Project project) throws Exception {
        return project.getId();
    }
}
