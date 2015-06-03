package ch.haeuslers.bookr.person.api;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface PersonService {

    @RolesAllowed("ADMINISTRATOR")
    void create(Person person);

    @PermitAll
    Person update(Person person);

    @PermitAll
    Optional<Person> read(String id);

    @RolesAllowed({"ADMINISTRATOR", "MANAGER"})
    List<Person> getAll();

    @PermitAll
    Optional<Person> getByPrincipalName(String principalName);

    @RolesAllowed("ADMINISTRATOR")
    void delete(Person person);

    @RolesAllowed("ADMINISTRATOR")
    void delete(String id);
}
