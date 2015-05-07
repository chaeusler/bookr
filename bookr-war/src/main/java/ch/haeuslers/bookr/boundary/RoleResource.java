package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.RoleService;
import ch.haeuslers.bookr.entity.Role;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("v1/roles")
public class RoleResource {

    @Inject
    private RoleService roleService;

    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addRoleToPerson(@QueryParam("personID") String personId, String roleType) {
        roleService.addRoleToPerson(personId, Role.Type.valueOf(roleType));
        return Response.created(URI.create("blablablab TODO")).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json", "application/xml"})
    public Response update(@PathParam("id") String personId, Role role) {
        // TODO verify id
        //roleService.update(role);
        return Response.noContent().build();
    }


    @GET
    public List<Role> getAll() {
        return roleService.getAll();
    }



}
