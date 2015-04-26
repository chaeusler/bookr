package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.PersonService;
import ch.haeuslers.bookr.entity.Person;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("v1/persons")
@Consumes({"application/json", "application/xml"})
@Produces({"application/json", "application/xml"})
public class PersonResource {

    @Inject
    PersonService personService;

    @PUT
    @Path("{id}")
    @RolesAllowed({"USER", "ADMINISTRATOR"})
    public Person update(@PathParam("id") String personId, Person person) {
        return personService.update(person);
    }

    @POST
    @RolesAllowed("ADMINISTRATOR")
    public Person create(Person person) {
        return personService.create(person);
    }

    @GET
    @RolesAllowed({"ADMINISTRATOR", "MANAGER"})
    public List<Person> getAll() {
        return personService.getAll();
    }
}
