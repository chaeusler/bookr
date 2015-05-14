package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Person;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.Optional;

@Stateless
@DeclareRoles({"ADMINISTRATOR", "USER", "MANAGER"})
public class PersonService {

    @Inject
    EntityManager em;

    @EJB
    PasswordService passwordService;

    @Resource
    SessionContext context;

    @RolesAllowed("ADMINISTRATOR")
    public void create(Person person) {
        em.persist(person);
    }

    @PermitAll
    public Person update(Person person) {
        if (context.isCallerInRole("ADMINISTRATOR")
            || context.getCallerPrincipal().getName().equalsIgnoreCase(person.getPrincipalName())) {
            return em.merge(person);
        } else {
            throw new EJBAccessException("only for administrators or the user itself");
        }
    }

    @PermitAll
    public Optional<Person> read(String id) {
        return Optional.ofNullable(em.find(Person.class, id));
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
        Optional<Person> person = read(personId);

        if (!person.isPresent()) {
            return;// TODO what do?
        }
        if (context.isCallerInRole("ADMINISTRATOR")
            || context.getCallerPrincipal().getName().equals(person.get().getPrincipalName())) {
            passwordService.updatePassword(person.get(), password);
        } else {
            throw new EJBAccessException("only for administrators or the user itself");
        }
    }

    @RolesAllowed("ADMINISTRATOR")
    public void delete(Person person) {
        // TODO don't remove - set inactive instead
        person = em.merge(person);
        em.remove(person);
    }

    @RolesAllowed("ADMINISTRATOR")
    public void delete(String id) {
        // TODO don't remove - set inactive instead
        em.remove(read(id).get());
    }

    // TODO when deleting a person the roles need to be deleted too
}
