package ch.haeuslers.bookr.control;

/**
 * Represents an entity change.
 *
 * @param <ENTITY> the Entity Type.
 */
public class ChangeEvent<ENTITY> {

    public enum ChangeType {
        CREATE,
        UPDATE,
        DELETE
    }

    private final Class<ENTITY> entityType;
    private final String entityId;
    private final ChangeType changeType;

    public ChangeEvent(Class<ENTITY> entityType, String entityId, ChangeType changeType) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.changeType = changeType;
    }

    public Class<ENTITY> getEntityType() {
        return entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    @Override
    public String toString() {
        return changeType + " " + entityType.getSimpleName() + " " + entityId;
    }
}
