package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Booking;
import ch.haeuslers.bookr.entity.Role;
import ch.haeuslers.bookr.entity.Person;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BookingService {

    @PersistenceContext(unitName = "bookr")
    EntityManager em;

    @Resource
    SessionContext context;

    @EJB
    PersonService personService;

    public Booking persist(Booking booking) throws IllegalAccessException {
        ensureEditRights(booking);

        if (hasOverlappingBookings(booking)) {
            throw new IllegalStateException("there are overlapping bookings");
            //return; // Todo throw Business Exceptios
        }
        em.persist(booking);
        return booking;
    }

    public Booking merge(Booking booking) throws IllegalAccessException {
        ensureEditRights(booking);

        if (hasOverlappingBookings(booking)) {
            //return; // Todo throw Business Exceptios
        }
        return em.merge(booking);
    }

    public List<Booking> listMine() {
        String principalName = context.getCallerPrincipal().getName();
        Person person = personService.getByPrincipalName(principalName);
        return em.createNamedQuery(Booking.QUERY_FIND_ALL_FOR_USER, Booking.class).
            setParameter("user", person).
            getResultList();
    }

    private boolean hasOverlappingBookings(Booking booking) {
        return !em.createNamedQuery(Booking.QUERY_FIND_OVERLAPPING)
            .setParameter("startDate", booking.getStart())
            .setParameter("endDate", booking.getEnd())
            .getResultList()
            .isEmpty();
        // TODO use count in DB
    }

    private void ensureEditRights(Booking booking) throws IllegalAccessException {
        if (hasOnlyUserRole()) {
            String principalName = context.getCallerPrincipal().getName();
            if (!principalName.equals(booking.getPerson().getPrincipalName())) {
                // abort because principal with the the only role user can't edit foreign bookings
                throw new IllegalAccessException("not allowed to edit foreign bookings");
            }
        }
    }

    private boolean hasOnlyUserRole() {
        return hasRole(Role.Type.USER) && !hasRole(Role.Type.MANAGER) && !hasRole(Role.Type.ADMINISTRATOR);
    }

    private boolean hasRole(Role.Type role) {
        return context.isCallerInRole(role.toString());
    }
}
