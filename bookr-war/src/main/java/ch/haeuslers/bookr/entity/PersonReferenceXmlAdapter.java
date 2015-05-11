package ch.haeuslers.bookr.entity;

import ch.haeuslers.bookr.control.PersonService;

import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PersonReferenceXmlAdapter extends XmlAdapter<String, Person> {

    @Inject
    PersonService personService;

    @Override
    public Person unmarshal(String personId) throws Exception {
        return personService
            .read(personId)
            .orElseThrow(() -> new RuntimeException("unable to unmarshal person.id - entity not found"));
    }

    @Override
    public String marshal(Person person) throws Exception {
        return person.getId();
    }
}
