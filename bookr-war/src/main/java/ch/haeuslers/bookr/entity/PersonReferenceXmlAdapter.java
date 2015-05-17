package ch.haeuslers.bookr.entity;

import ch.haeuslers.bookr.util.BeanLocator;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Optional;

public class PersonReferenceXmlAdapter extends XmlAdapter<String, Person> {

    Optional<EntityManager> em = Optional.empty();

    @Override
    public Person unmarshal(String personId) throws Exception {
        ensureEntityManager();

        return Optional.ofNullable(em.get().find(Person.class, personId))
            .orElseThrow(() -> new RuntimeException("unable to unmarshal person.id - entity not found"));
    }

    @Override
    public String marshal(Person person) throws Exception {
        return person.getId();
    }

    private void ensureEntityManager() {
        if (em.isPresent()) {
            return;
        }

        em = Optional.of((EntityManager) BeanLocator.lookup("java:/entitymanager/bookr"));
    }
}
