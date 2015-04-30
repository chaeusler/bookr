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
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("v1/persons")
@ValidateOnExecution(type = ExecutableType.NON_GETTER_METHODS)
public class PersonResource {

    @Inject
    PersonService personService;

    @Inject
    PasswordService passwordService;

    @POST
    @Path("{id}")
    @Consumes({"application/json", "application/xml"})
    @RolesAllowed("ADMINISTRATOR")
    public void create(@PathParam("id") String personId, Person person) {
        // TODO verify id
        personService.create(person);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json", "application/xml"})
    @RolesAllowed({"USER", "ADMINISTRATOR"})
    public void update(@PathParam("id") String personId, Person person) {
        // TODO verify id
        personService.update(person);
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @RolesAllowed({"ADMINISTRATOR", "MANAGER"})
    public List<Person> getAll() {
        return personService.getAll();
    }

    @POST
    @Path("{id}/setPassword")
    public void setPassword(@PathParam("id") String personId, @FormParam("password") @NotNull String password, @Context SecurityContext securityContext) throws AuthException{
        Person person = personService.find(personId);

        if (securityContext.isUserInRole("ADMINISTRATOR") || securityContext.getUserPrincipal().getName().equals(person.getPrincipalName())) {
            passwordService.updatePassword(person, password);
        } else {
            throw new AuthException();
        }
    }
}
