package ch.haeuslers.bookr.person.service;

import ch.haeuslers.bookr.core.common.PerformanceLogged;
import ch.haeuslers.bookr.person.api.Person;
import ch.haeuslers.bookr.person.api.PersonService;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
public class PersonServiceBean implements PersonService {

    @Inject
    EntityManager em;

    @Resource
    SessionContext context;

    @Override
    @RolesAllowed("ADMINISTRATOR")
    public void create(Person person) {
        em.persist(person);
    }

    @Override
    @PermitAll
    public Person update(Person person) {
        if (context.isCallerInRole("ADMINISTRATOR")
            || context.getCallerPrincipal().getName().equalsIgnoreCase(person.getName())) {
            return em.merge(person);
        } else {
            throw new EJBAccessException("only for administrators or the user itself");
        }
    }

    @Override
    @PermitAll
    public Optional<Person> read(String id) {
        return Optional.ofNullable(em.find(Person.class, id));
    }

    @Override
    @RolesAllowed({"ADMINISTRATOR", "MANAGER"})
    public List<Person> getAll() {
        return em.createNamedQuery(Person.QUERY_ALL, Person.class).getResultList();
    }

    @Override
    @PermitAll
    public Optional<Person> getByPrincipalName(String principalName) {
        return em.createNamedQuery(Person.QUERY_FIND_BY_PRINCIPAL_NAME, Person.class)
            .setParameter("principalName", principalName)
            .getResultList()
            .stream()
            .findFirst();
    }

    @Override
    @RolesAllowed("ADMINISTRATOR")
    public void delete(Person person) {
        // TODO don't remove - set inactive instead
        person = em.merge(person);
        em.remove(person);
    }

    @Override
    @RolesAllowed("ADMINISTRATOR")
    public void delete(String id) {
        // TODO don't remove - set inactive instead
        Person person = read(id).get();
        em.remove(person);
    }
}
