package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Booking;
import ch.haeuslers.bookr.entity.Person;
import ch.haeuslers.bookr.entity.Role;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
@DeclareRoles({"USER", "MANAGER", "ADMINISTRATOR"})
@RolesAllowed({"USER", "MANAGER", "ADMINISTRATOR"})
public class BookingService implements Serializable {

    private static final long serialVersionUID = 1;

    @Inject
    private transient EntityManager em;

    @Resource
    private transient SessionContext context;

    @EJB
    private transient PersonService personService;

    public void create(final Booking booking) {
        ensureEditRights(booking);

        // TODO validate person needs to be in project

        if (hasOverlappingBookings(booking)) {
            throw new IllegalArgumentException("there are overlapping bookings");
        }
        em.persist(booking);
    }

    public Optional<Booking> read(final String id) {
        return Optional.ofNullable(em.find(Booking.class, id));
    }

    public Booking update(final Booking booking) {
        ensureEditRights(booking);

        // TODO validate person needs to be in project

        if (hasOverlappingBookings(booking)) {
            throw new IllegalArgumentException("overlapping bokking not allowed");
        }
        return em.merge(booking);
    }

    public void delete(final String bookingId) {
        read(bookingId).ifPresent(em::remove);
    }

    public List<Booking> listMine() {
        final String principalName = context.getCallerPrincipal().getName();
        final Optional<Person> person = personService.getByPrincipalName(principalName);
        if (person.isPresent()) {
            return em.createNamedQuery(Booking.QUERY_FIND_ALL_FOR_USER, Booking.class).
                setParameter("user", person.get()).
                getResultList();
        }
        return Collections.emptyList();
    }

    private boolean hasOverlappingBookings(final Booking booking) {
        return !em.createNamedQuery(Booking.QUERY_FIND_OVERLAPPING)
            .setParameter("startDate", booking.getStart())
            .setParameter("endDate", booking.getEnd())
                .getResultList()
                .isEmpty();
        // TODO use count in DB
    }

    private void ensureEditRights(final Booking booking) {
        if (hasOnlyUserRole()) {
            final String principalName = context.getCallerPrincipal().getName();
            if (!principalName.equals(booking.getPerson().getName())) {
                // abort because principal with the the only role user can't edit foreign bookings
                throw new EJBAccessException("not allowed to edit foreign bookings");
            }
        }
    }

    private boolean hasOnlyUserRole() {
        return hasRole(Role.USER) && !hasRole(Role.MANAGER) && !hasRole(Role.ADMINISTRATOR);
    }

    private boolean hasRole(final Role role) {
        return context.isCallerInRole(role.toString());
    }

    @RolesAllowed({"MANAGER", "ADMINISTRATOR"})
    public List<Booking> getAll() {
        return em.createNamedQuery(Booking.QUERY_FIND_ALL, Booking.class).getResultList();
    }
}
