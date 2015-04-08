package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "BOOKR_ROLE")
@IdClass(Role.RoleId.class)
@NamedQueries(
    @NamedQuery(name = Role.FIND_ALL_FOR_PERSON_ID, query = "SELECT r FROM Role r WHERE r.person.id = :personId")
)
public class Role {

    public static final String FIND_ALL_FOR_PERSON_ID = "Role.findAllForPerson";

    @Id
    @ManyToOne(optional = false)
    private Person person;

    @Id
    @Enumerated(EnumType.STRING)
    @NotNull
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
        return Objects.hash(person, type);
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
