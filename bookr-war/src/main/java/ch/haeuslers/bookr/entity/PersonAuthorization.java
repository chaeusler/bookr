package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElementRef;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "BOOKR_PERSON_AUTHORIZATION")
@NamedQueries(@NamedQuery(name = PersonAuthorization.GET_FOR_PERSON_ID, query = "FROM PersonAuthorization AS pa WHERE pa.id = :personId"))
public class PersonAuthorization implements Serializable {

    public static final String GET_FOR_PERSON_ID = "PersonAuthorization.getForPersonID";

    @Id
    @OneToOne
    @XmlElementRef(name = "person-id")
    private Person person;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @JoinTable(
        name = "BOOKR_PERSON_AUTHORIZATION_ROLE",
        joinColumns = @JoinColumn(name = "person_authorization_id")
    )
    private Set<RoleType> roles;

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public Set<RoleType> getRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }

    public void setRoles(Set<RoleType> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonAuthorization)) return false;
        PersonAuthorization that = (PersonAuthorization) o;
        return Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person);
    }
}
