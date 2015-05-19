package ch.haeuslers.bookr.entity;

import javax.persistence.EntityManager;
import java.util.Optional;

public class PersonReferenceXmlAdapter extends EntityXmlAdapter<String, Person> {

    @Override
    public Person unmarshal(String personId) throws Exception {
        EntityManager em = getEntityManager();

        return Optional.ofNullable(em.find(Person.class, personId))
            .orElseThrow(() -> new RuntimeException("unable to unmarshal person.id - entity not found"));
    }

    @Override
    public String marshal(Person person) throws Exception {
        return person.getId();
    }

}
