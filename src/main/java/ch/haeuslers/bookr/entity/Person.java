package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "BOOKR_USER")
@NamedQueries({
        @NamedQuery(name = "User.findByPrincipalName", query = "SELECT u FROM Person u WHERE u.principalName = :principalName")
})
public class Person extends BaseEntity {

    @Id
    public String id;

    @NotNull
    private String principalName;

    @OneToMany(mappedBy = "person")
    private Set<Role> roles;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
