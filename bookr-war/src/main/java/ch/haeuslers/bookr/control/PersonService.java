package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Person;
import ch.haeuslers.bookr.entity.Role;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Stateless
@DeclareRoles({"ADMINISTRATOR", "USER", "MANAGER"})
public class PersonService {

    @PersistenceContext(unitName = "bookr")
    EntityManager em;

    @EJB
    PasswordService passwordService;

    @Resource
    SessionContext context;

    @RolesAllowed("ADMINISTRATOR")
    public Person create(Person person) {
        if (person.getId() == null || person.getId().isEmpty()) {
            person.setId(UUID.randomUUID().toString());
        }
        em.persist(person);
        return person;
    }

    @PermitAll
    public Person update(Person person) {
        // TODO ok if the principal is the person
        // TODO ok if the principal is in role Admin
        return em.merge(person);
    }

    @PermitAll
    public Person find(String id) {
        return em.find(Person.class, id);
    }

    @RolesAllowed({"ADMINISTRATOR", "MANAGER"})
    public List<Person> getAll() {
        return em.createNamedQuery(Person.QUERY_ALL, Person.class).getResultList();
    }

    @PermitAll
    public Optional<Person> getByPrincipalName(String principalName) {
        return em.createNamedQuery(Person.QUERY_FIND_BY_PRINCIPAL_NAME, Person.class)
            .setParameter("principalName", principalName)
            .getResultList()
            .stream()
            .findFirst();
    }

    @PermitAll // secured inside
    public void setPassword(String personId, String password) throws AuthException {
        Person person = find(personId);

        if (context.isCallerInRole("ADMINISTRATOR") || context.getCallerPrincipal().getName().equals(person.getPrincipalName())) {
            passwordService.updatePassword(person, password);
        } else {
            throw new AuthException();
        }
    }
    // TODO when deleting a person the roles need to be deleted too
}
