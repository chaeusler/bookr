package ch.haeuslers.bookr.authorization.api;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "BOOKR_PASSWORD")
public class Password implements Serializable {

    @Id
    @Column(name = "authorization_id")
    private String id; // needed to get the mapping working

    @OneToOne(optional = false)
    @JoinColumn(name = "authorization_id")
    private Authorization authorization;

    @NotNull
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
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
        return Objects.equals(authorization, password1.authorization) && Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorization, password);
    }
}
