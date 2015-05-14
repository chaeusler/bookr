package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.PasswordService;
import ch.haeuslers.bookr.control.PersonService;
import ch.haeuslers.bookr.entity.Person;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.auth.message.AuthException;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("v1/persons")
@ValidateOnExecution(type = ExecutableType.NON_GETTER_METHODS)
public class PersonResource {

    @Inject
    PersonService personService;

    @Inject
    PasswordService passwordService;

    @GET
    @Produces({"application/json", "application/xml"})
    public List<Person> getAll() {
        return personService.getAll();
    }

    @POST
    @Path("{id}")
    @Consumes({"application/json", "application/xml"})
    public void create(@PathParam("id") String personId, Person person) {
        // TODO verify id
        personService.create(person);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json", "application/xml"})
    public void update(@PathParam("id") String personId, Person person) {
        // TODO verify id
        personService.update(person);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        personService.delete(id);
    }

    @POST
    @Path("{id}/setPassword")
    public void setPassword(@PathParam("id") String personId, @FormParam("password") @NotNull String password) throws AuthException{
       personService.setPassword(personId, password);
    }
}
