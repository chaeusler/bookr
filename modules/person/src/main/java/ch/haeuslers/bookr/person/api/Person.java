package ch.haeuslers.bookr.person.api;

import ch.haeuslers.bookr.core.api.ValidUUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "BOOKR_PERSON")
@NamedQueries({
        @NamedQuery(name = Person.QUERY_FIND_BY_PRINCIPAL_NAME, query = "SELECT u FROM Person u WHERE u.name = :principalName"),
        @NamedQuery(name = Person.QUERY_ALL, query = "FROM Person")
})
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {

    public static final String QUERY_FIND_BY_PRINCIPAL_NAME = "Person.findByPrincipalName";
    public static final String QUERY_ALL = "Person.findAll";

    @Id
    @XmlAttribute
    @ValidUUID
    private String id;

    @NotNull
    @Column(unique = true)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String principalName) {
        this.name = principalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
