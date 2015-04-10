package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "BOOKR_PERSON_IN_PROJECT")
@IdClass(PersonInProject.PersonInProjectId.class)
public class PersonInProject {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonInProject that = (PersonInProject) o;
        return Objects.equals(person, that.person) && Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, project);
    }

    public static class PersonInProjectId implements Serializable {
        Person person;
        Project project;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PersonInProjectId that = (PersonInProjectId) o;
            return Objects.equals(person, that.person) && Objects.equals(project, that.project);
        }

        @Override
        public int hashCode() {
            return Objects.hash(person, project);
        }
    }
}
