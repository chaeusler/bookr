package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "BOOKR_PERSON")
@NamedQueries({
        @NamedQuery(name = Person.QUERY_FIND_BY_PRINCIPAL_NAME, query = "SELECT u FROM Person u WHERE u.principalName = :principalName"),
        @NamedQuery(name = Person.QUERY_ALL, query = "SELECT FROM Person p")
})
public class Person extends BaseEntity {

    public static final String QUERY_FIND_BY_PRINCIPAL_NAME = "Person.findByPrincipalName";
    public static final String QUERY_ALL = "Person.findAll";

    @Id
    public String id;

    @NotNull
    private String principalName;

    @OneToMany(mappedBy = "person")
    private Set<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!id.equals(person.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
