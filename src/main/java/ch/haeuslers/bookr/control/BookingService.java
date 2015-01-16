package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Booking;
import ch.haeuslers.bookr.entity.Role;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;

@Stateless
@NamedQuery(name="findOverlappingBookings",
        query="SELECT b " +
                "FROM Booking b " +
                "WHERE b.start >= :startDate AND b.end <= :startDate AND b.start >= :endDate AND b.end >= :endDate" +
                "   OR b.start <= :startDate AND b.end <= :startDate AND b.start >= :endDate AND b.end <= :endDate")
public class BookingService {

    @PersistenceContext(unitName="bookr")
    EntityManager em;

    @Resource
    private SessionContext context;


    public void book(Booking booking) throws IllegalAccessException {
        ensureEditRights(booking);

        // check validity:
        // - the user only can book one booking in a timerange

        em.persist(booking);
    }

    private void ensureEditRights(Booking booking) throws IllegalAccessException {
        if (hasOnlyUserRole()) {
            String principalName = context.getCallerPrincipal().getName();
            if (!principalName.equals(booking.getUser().getPrincipalName())) {
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
