package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.PersonService;
import ch.haeuslers.bookr.entity.Person;

import javax.inject.Inject;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("v1/persons")
@ValidateOnExecution(type = ExecutableType.NON_GETTER_METHODS)
public class PersonResource {

    public static final String PATH_ID = "{id}";
    public static final String PATH_PARAM_ID = "id";

    @Inject
    private transient PersonService personService;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Person> getAll() {
        return personService.getAll();
    }

    @GET
    @Path(PATH_ID)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Person read(final @PathParam(PATH_PARAM_ID) String personId) {
        return personService.read(personId).orElseThrow(NotFoundException::new);
    }

    @POST
    @Path(PATH_ID)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void create(final @PathParam(PATH_PARAM_ID) String personId, final Person person) {
        // TODO verify id
        personService.create(person);
    }

    @PUT
    @Path(PATH_ID)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void update(final @PathParam(PATH_PARAM_ID) String personId, final Person person) {
        // TODO verify id
        personService.update(person);
    }

    @DELETE
    @Path(PATH_ID)
    public void delete(final @PathParam(PATH_PARAM_ID) String id) {
        personService.delete(id);
    }

}
