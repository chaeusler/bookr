package ch.haeuslers.bookr.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {

    public long timestamp;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }
}
