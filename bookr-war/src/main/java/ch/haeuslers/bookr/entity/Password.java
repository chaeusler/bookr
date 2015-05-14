package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "PASSWORD")
public class Password implements Serializable {

    @Id
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    private Person person;

    @NotNull
    private String password;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(person, password1.person) && Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, password);
    }
}
