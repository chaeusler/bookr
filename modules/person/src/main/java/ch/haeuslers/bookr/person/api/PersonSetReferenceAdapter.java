package ch.haeuslers.bookr.person.api;

import ch.haeuslers.bookr.core.api.EntityXmlAdapter;

import javax.persistence.EntityManager;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonSetReferenceAdapter extends EntityXmlAdapter<Set<String>, Set<Person>> {

    @Override
    public Set<Person> unmarshal(Set<String> personIds) throws Exception {
        EntityManager em = getEntityManager();

        return personIds
            .stream()
            .map(id -> em.find(Person.class, id))
            .filter(person -> person != null)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<String> marshal(Set<Person> persons) throws Exception {
        return persons.stream().map(Person::getId).collect(Collectors.toSet());
    }
}
