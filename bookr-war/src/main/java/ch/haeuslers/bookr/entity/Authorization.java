package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "BOOKR_AUTHORIZATION")
@NamedQueries(
    @NamedQuery(name = Authorization.QUERY_ALL, query = "FROM Authorization")
)
public class Authorization implements Serializable {

    public static final String QUERY_ALL = "Authorization.findAll";

    @Id
    @OneToOne(optional = false)
    private Person person;

    @NotNull
    @Column(unique = true)
    private String principalName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @JoinTable(
        name = "BOOKR_AUTHORIZATION_ROLE",
        joinColumns = @JoinColumn(name = "authorization_id")
    )
    private Set<Role> roles;

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public Set<Role> getRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authorization)) return false;
        Authorization that = (Authorization) o;
        return Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person);
    }
}
