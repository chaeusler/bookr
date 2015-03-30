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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return person.equals(role.person) && type == role.type;

    }

    @Override
    public int hashCode() {
        int result = person.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    static class RoleId implements Serializable {
        Person person;
        Type type;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RoleId roleId = (RoleId) o;

            return person.equals(roleId.person) && type == roleId.type;

        }

        @Override
        public int hashCode() {
            int result = person.hashCode();
            result = 31 * result + type.hashCode();
            return result;
        }
    }
}
