package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BOOKR_PERSON_IN_PROJECT")
@IdClass(PersonInProject.PersonInProjectId.class)
public class PersonInProject extends BaseEntity {

    @Id
    @OneToOne
    private Person person;

    @Id
    @OneToOne
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

        if (!person.equals(that.person)) return false;
        if (!project.equals(that.project)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = person.hashCode();
        result = 31 * result + project.hashCode();
        return result;
    }

    public static class PersonInProjectId implements Serializable {
        Person person;
        Project project;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PersonInProjectId that = (PersonInProjectId) o;

            return person.equals(that.person) && project.equals(that.project);

        }

        @Override
        public int hashCode() {
            int result = person.hashCode();
            result = 31 * result + project.hashCode();
            return result;
        }
    }
}
