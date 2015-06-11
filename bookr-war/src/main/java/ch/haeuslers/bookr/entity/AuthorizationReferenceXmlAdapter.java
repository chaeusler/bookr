package ch.haeuslers.bookr.entity;

import javax.persistence.EntityManager;
import java.util.Optional;

public class AuthorizationReferenceXmlAdapter extends EntityXmlAdapter<String, Authorization> {

    @Override
    public Authorization unmarshal(String authorizationId) throws Exception {
        EntityManager em = getEntityManager();

        return Optional.ofNullable(em.find(Authorization.class, authorizationId))
            .orElseThrow(() -> new RuntimeException("unable to unmarshal authorization.id - entity not found"));
    }

    @Override
    public String marshal(Authorization authorization) throws Exception {
        return authorization.getPerson().getId();
    }
}