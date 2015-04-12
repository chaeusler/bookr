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
@RolesAllowed({"USER", "MANAGER", "ADMINISTRATOR"})
@DeclareRoles({"USER", "MANAGER", "ADMINISTRATOR"})
public class PersonResource {

    @Inject
    PersonService personService;

    @PUT
    public Person create(Person person) {
        return personService.persist(person);
    }

    @GET
    public List<Person> getAll() {
        return personService.getAll();
    }
}
