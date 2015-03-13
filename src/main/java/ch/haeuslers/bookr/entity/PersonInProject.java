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

    public static class PersonInProjectId implements Serializable {
        Person person;
        Project project;
    }
}
