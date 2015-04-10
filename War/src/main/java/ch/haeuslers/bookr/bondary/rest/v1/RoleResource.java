package ch.haeuslers.bookr.bondary.rest.v1;

import ch.haeuslers.bookr.control.RoleService;
import ch.haeuslers.bookr.entity.Role;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("v1/roles")
@Consumes({"application/json", "application/xml"})
@Produces({"application/json", "application/xml"})
@RolesAllowed({"USER", "MANAGER", "ADMINISTRATOR"})
@DeclareRoles({"USER", "MANAGER", "ADMINISTRATOR"})
public class RoleResource {

    @Inject
    private RoleService roleService;

    @GET
    public List<Role> getAll() {
        return roleService.getAll();
    }
}
