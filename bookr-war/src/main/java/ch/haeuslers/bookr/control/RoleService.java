package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Person;
import ch.haeuslers.bookr.entity.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless
public class RoleService {

    @PersistenceContext(unitName = "bookr")
    EntityManager em;

    @RolesAllowed("ADMINISTRATOR")
    public Role addRoleToPerson(String personId, Role.Type roleType) {
        Person person = em.find(Person.class, personId);
        if (person == null) {
            // TODO throw BusinessException
            throw new IllegalStateException("unknown person with id=" + personId);
        }

        Role role = new Role();
        role.setPerson(person);
        role.setType(roleType);
        em.persist(role);
        return role;
    }

    @PermitAll
    public List<Role> findRolesForPerson(String personId) {
        return em.createNamedQuery(Role.FIND_ALL_FOR_PERSON_ID, Role.class)
            .setParameter("personId", personId)
            .getResultList();
    }

    @RolesAllowed({"ADMINISTRATOR", "MANAGER"})
    public List<Role> getAll() {
        return em.createNamedQuery(Role.QUERY_ALL, Role.class).getResultList();
    }

    @RolesAllowed("ADMINISTRATOR")
    public void removeRoleFromPerson(String personId, Role.Type roleType) {
        em.createNamedQuery(Role.DELETE_ROLE_FOR_PERSON)
            .setParameter("personId", personId)
            .setParameter("roleType", roleType)
            .executeUpdate();
    }
}
