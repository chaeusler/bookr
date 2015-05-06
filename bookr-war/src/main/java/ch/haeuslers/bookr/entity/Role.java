package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "BOOKR_ROLE")
@IdClass(Role.RoleId.class)
@NamedQueries({
    @NamedQuery(name = Role.FIND_ALL_FOR_PERSON_ID, query = "SELECT r FROM Role r WHERE r.person.id = :personId"),
    @NamedQuery(name = Role.QUERY_ALL, query = "FROM Role"),
    @NamedQuery(name = Role.DELETE_ROLE_FOR_PERSON, query = "DELETE FROM Role r WHERE r.person.id = :personId AND r.type = :roleType")
})
@XmlRootElement(name = "role")
@XmlAccessorType(XmlAccessType.FIELD)
public class Role implements Serializable {

    public static final String FIND_ALL_FOR_PERSON_ID = "Role.findAllForPerson";

    public static final String QUERY_ALL = "Role.queryAll";

    public static final String DELETE_ROLE_FOR_PERSON = "Role.deleteRoleForPerson";

    @Id
    @ManyToOne(optional = false)
    @XmlElementRef(name = "person-id")
    @XmlJavaTypeAdapter(PersonReferenceXmlAdapter.class)
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
        return Objects.equals(person, role.person) &&
            Objects.equals(type, role.type);
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
            if (!(o instanceof RoleId)) return false;
            RoleId roleId = (RoleId) o;
            return Objects.equals(person, roleId.person) &&
                Objects.equals(type, roleId.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(person, type);
        }
    }
}
