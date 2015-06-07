package ch.haeuslers.bookr.control;

import java.util.Optional;

public interface CRUDService<ENTITY extends Object> {

    void create(ENTITY entity);

    Optional<? extends ENTITY> read(String entityId);

    ENTITY update(ENTITY entity);

    void delete(String entityId);
}
