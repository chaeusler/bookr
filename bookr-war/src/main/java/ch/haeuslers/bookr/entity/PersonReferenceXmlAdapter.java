package ch.haeuslers.bookr.entity;

import ch.haeuslers.bookr.control.PersonService;

import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Optional;
import java.util.UUID;

public class PersonReferenceXmlAdapter extends XmlAdapter<String, Person> {

    @Inject
    PersonService personService;

    @Override
    public Person unmarshal(String personId) throws Exception {
        Optional<Person> person = personService.find(personId);
        if (person.isPresent()) {
            return person.get();
        }
        throw new RuntimeException("unable to unmarshal person.id - entity not found");
    }

    @Override
    public String marshal(Person person) throws Exception {
        return person.getId().toString();
    }
}
