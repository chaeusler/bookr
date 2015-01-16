package ch.haeuslers.bookr.entity;

import javax.persistence.*;

@Entity
public class Role {

    public enum Type {
        USER,
        MANAGER,
        ADMINISTRATOR
    }

    @Id
    private String id;

    @ManyToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;


}
