package ch.haeuslers.bookr.project.rest;

import ch.haeuslers.bookr.project.api.Project;
import ch.haeuslers.bookr.project.service.ProjectService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("v1/projects")
public class ProjectResource {

    @Inject
    ProjectService projectService;

    @GET
    @Produces({"application/json", "application/xml"})
    public List<Project> getAll() {
        return projectService.getAll();
    }

    @POST
    @Path("{id}")
    @Consumes({"application/json", "application/xml"})
    public void create(@PathParam("id") String projectId, Project project) {
        // TODO verify id
        projectService.create(project);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json", "application/xml"})
    public void update(@PathParam("id") String projectId, Project project) {
        // TODO verify id
        projectService.update(project);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        projectService.delete(id);
    }
}
