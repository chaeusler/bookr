package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BOOKR_ROLE")
@IdClass(Role.RoleId.class)
public class Role extends BaseEntity {

    @Id
    @ManyToOne(optional = false)
    private Person person;

    @Id
    @Enumerated(EnumType.STRING)
    private Type type;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        USER,
        MANAGER,
        ADMINISTRATOR
    }

    static class RoleId implements Serializable {
        Person person;
        Type type;
    }
}
