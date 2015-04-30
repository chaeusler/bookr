package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "BOOKR_PERSON")
@NamedQueries({
        @NamedQuery(name = Person.QUERY_FIND_BY_PRINCIPAL_NAME, query = "SELECT u FROM Person u WHERE u.principalName = :principalName"),
        @NamedQuery(name = Person.QUERY_ALL, query = "FROM Person")
})
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {

    public static final String QUERY_FIND_BY_PRINCIPAL_NAME = "Person.findByPrincipalName";
    public static final String QUERY_ALL = "Person.findAll";

    @Id
    @UUID
    @XmlAttribute
    private String id;

    @NotNull
    private String principalName;

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
