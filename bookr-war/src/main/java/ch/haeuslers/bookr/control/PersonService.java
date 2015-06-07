package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.common.Audited;
import ch.haeuslers.bookr.common.performance.PerformanceLogged;
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
import java.util.List;
import java.util.Optional;

@Stateless
@DeclareRoles({"ADMINISTRATOR", "USER", "MANAGER"})
@PerformanceLogged
@Audited
public class PersonService {

    @Inject
    private transient EntityManager em;

    @EJB
    private transient PasswordService passwordService;

    @Resource
    private transient SessionContext context;

    @RolesAllowed("ADMINISTRATOR")
    public void create(final Person person) {
        em.persist(person);
    }

    @PermitAll
    public Person update(final Person person) {
        if (context.isCallerInRole("ADMINISTRATOR")
            || context.getCallerPrincipal().getName().equalsIgnoreCase(person.getName())) {
            return em.merge(person);
        } else {
            throw new EJBAccessException("only for administrators or the user itself");
        }
    }

    @PermitAll
    public Optional<Person> read(final String id) {
        return Optional.ofNullable(em.find(Person.class, id));
    }

    @RolesAllowed({"ADMINISTRATOR", "MANAGER"})
    public List<Person> getAll() {
        return em.createNamedQuery(Person.QUERY_ALL, Person.class).getResultList();
    }

    @PermitAll
    public Optional<Person> getByPrincipalName(final String principalName) {
        return em.createNamedQuery(Person.QUERY_FIND_BY_PRINCIPAL_NAME, Person.class)
            .setParameter("principalName", principalName)
            .getResultList()
            .stream()
            .findFirst();
    }

    @RolesAllowed("ADMINISTRATOR")
    public void delete(final Person person) {
        // TODO don't remove - set inactive instead
        final Person p = em.merge(person);
        em.remove(p);
    }

    @RolesAllowed("ADMINISTRATOR")
    public void delete(final String id) {
        // TODO don't remove - set inactive instead
        read(id).ifPresent(this::delete);
    }
}
