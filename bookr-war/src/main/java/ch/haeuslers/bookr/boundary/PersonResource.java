package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.PasswordService;
import ch.haeuslers.bookr.control.PersonService;
import ch.haeuslers.bookr.entity.Person;

import javax.inject.Inject;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.*;
import java.util.List;

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

    @GET
    @Path("{id}")
    @Produces({"application/json", "application/xml"})
    public Person read(@PathParam("id") String personId) {
        return personService.read(personId).orElseThrow(NotFoundException::new);
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

}
