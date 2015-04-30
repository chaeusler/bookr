package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BaseEntity {

    public Timestamp timestamp;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
