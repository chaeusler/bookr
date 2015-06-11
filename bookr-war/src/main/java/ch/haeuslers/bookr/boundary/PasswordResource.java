package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.PasswordService;
import ch.haeuslers.bookr.entity.Password;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("v1/passwords")
public class PasswordResource {

    public static final String PATH_ID = "{id}";
    public static final String PATH_PARAM_ID = "id";

    @Inject
    private PasswordService passwordService;

    @POST
    @Path(PATH_ID)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void create(@PathParam(PATH_PARAM_ID) String id, Password password) {
        passwordService.create(password);
    }

    @PUT
    @Path(PATH_ID)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void update(@PathParam(PATH_PARAM_ID) String id, Password password) {
        passwordService.updatePassword(password);
    }
}
