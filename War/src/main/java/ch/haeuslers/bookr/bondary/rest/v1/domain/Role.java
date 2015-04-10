package ch.haeuslers.bookr.bondary.rest.v1.domain;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "role")
public class Role {

    private Type type;

    @XmlElementRef
    private Person person;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public enum Type {
        USER,
        MANAGER,
        ADMINISTRATOR
    }
}
