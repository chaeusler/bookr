package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.ProjectService;
import ch.haeuslers.bookr.entity.Project;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("v1/projects")
public class ProjectResource {

    public static final String PATH_ID = "{id}";
    public static final String PATH_PARAM_ID = "id";

    @Inject
    private transient ProjectService projectService;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Project> getAll() {
        return projectService.getAll();
    }

    @POST
    @Path(PATH_ID)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void create(final @PathParam(PATH_PARAM_ID) String projectId, final Project project) {
        // TODO verify id
        projectService.create(project);
    }

    @PUT
    @Path(PATH_ID)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void update(final @PathParam(PATH_PARAM_ID) String projectId, final Project project) {
        // TODO verify id
        projectService.update(project);
    }

    @DELETE
    @Path(PATH_ID)
    public void delete(final @PathParam(PATH_PARAM_ID) String id) {
        projectService.delete(id);
    }
}
