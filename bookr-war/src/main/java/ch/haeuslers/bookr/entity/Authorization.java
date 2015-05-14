package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElementRef;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "AUTHORIZATION")
public class Authorization implements Serializable {

    @Id
    @OneToOne
    @XmlElementRef(name = "person-id")
    private Person person;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @JoinTable(
        name = "AUTHORIZATION_ROLE",
        joinColumns = @JoinColumn(name = "authorization_id")
    )
    private Set<Role> roles;

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
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
