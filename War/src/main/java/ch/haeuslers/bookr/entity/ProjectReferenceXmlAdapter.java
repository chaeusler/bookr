package ch.haeuslers.bookr.entity;

import ch.haeuslers.bookr.control.ProjectService;

import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ProjectReferenceXmlAdapter extends XmlAdapter<String, Project> {

    @Inject
    ProjectService projectService;

    @Override
    public Project unmarshal(String projectId) throws Exception {
        return projectService.find(projectId);
    }

    @Override
    public String marshal(Project project) throws Exception {
        return project.getId();
    }
}
