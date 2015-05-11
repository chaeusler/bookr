package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "BOOKR_PROJECT")
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project implements Serializable {

    @Id
    @XmlAttribute
    @ValidUUID
    private String id;

    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name="BOOKR_PROJECT_PERSON",
        joinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Person> persons;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getPersons() {
        if (persons == null) {
            persons = new HashSet<>();
        }
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
