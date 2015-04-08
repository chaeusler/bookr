package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Person;
import ch.haeuslers.bookr.entity.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;

@Stateless
public class RoleService {

    @PersistenceContext(unitName = "bookr")
    EntityManager em;

    public Role addRoleToPerson(String personId, Role.Type roleType) {
        Person person = em.find(Person.class, personId);
        if (person == null) {
            // TODO throw BusinessException
            throw new IllegalStateException("unknown person with id=" + personId);
        }

        Role role = new Role();
        role.setPerson(person);
        role.setType(roleType);
        if (person.getRoles() == null) {
            person.setRoles(new HashSet<>());
        }
        person.getRoles().add(role);
        em.persist(role);
        return role;
    }

}
