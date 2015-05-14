package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.AuthorizationService;
import ch.haeuslers.bookr.entity.Authorization;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import javax.ws.rs.*;

@Path("v1/authorizations")
public class AuthorizationResource {

    @Inject
    AuthorizationService authorizationService;

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
}
