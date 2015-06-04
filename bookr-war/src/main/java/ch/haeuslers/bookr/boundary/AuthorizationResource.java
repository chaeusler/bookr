package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.AuthorizationService;
import ch.haeuslers.bookr.entity.Authorization;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("v1/authorizations")
public class AuthorizationResource {

    public static final String PATH_ID = "{id}";
    public static final String PATH_PARAM_ID = "id";

    @Inject
    private transient AuthorizationService authorizationService;

    @POST
    public void create(final Authorization authorization) {
        authorizationService.create(authorization);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Authorization> readAll() {
        return authorizationService.readAll();
    }

    @GET
    @Path(PATH_ID)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Authorization read(final @PathParam(PATH_PARAM_ID) String id) {
        return authorizationService.read(id).orElseThrow(NotFoundException::new);
    }

    @PUT
    @Path(PATH_ID)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void update(final @PathParam(PATH_PARAM_ID) String id, final Authorization authorization) {
        authorizationService.update(authorization);
    }

    @DELETE
    @Path(PATH_ID)
    public void delete(final @PathParam(PATH_PARAM_ID) String id) {
        authorizationService.delete(id);
    }
}
