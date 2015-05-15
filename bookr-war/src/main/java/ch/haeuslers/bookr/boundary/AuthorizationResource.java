package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.AuthorizationService;
import ch.haeuslers.bookr.entity.Authorization;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import javax.ws.rs.*;
import java.util.List;

@Path("v1/authorizations")
public class AuthorizationResource {

    @Inject
    AuthorizationService authorizationService;

    @POST
    public void create(Authorization authorization) {
        authorizationService.create(authorization);
    }

    @GET
    @Produces({"application/json", "application/xml"})
    public List<Authorization> readAll() {
        return authorizationService.readAll();
    }

    @GET
    @Path("{id}")
    @Produces({"application/json", "application/xml"})
    public Authorization read(@PathParam("id") String id) {
        return authorizationService.read(id).orElseThrow(NotFoundException::new);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json", "application/xml"})
    public void update(@PathParam("id") String id, Authorization authorization) {
        authorizationService.update(authorization);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        authorizationService.delete(id);
    }
}
