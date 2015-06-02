package ch.haeuslers.bookr.booking.service;

import ch.haeuslers.bookr.booking.api.Booking;
import ch.haeuslers.bookr.core.common.Role;
import ch.haeuslers.bookr.person.api.Person;
import ch.haeuslers.bookr.person.service.PersonService;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
@DeclareRoles({"USER", "MANAGER", "ADMINISTRATOR"})
@RolesAllowed({"USER", "MANAGER", "ADMINISTRATOR"})
public class BookingService {

    @Inject
    EntityManager em;

    @Resource
    SessionContext context;

    @EJB
    PersonService personService;

    public void create(Booking booking) {
        ensureEditRights(booking);

        // TODO validate person needs to be in project

        if (hasOverlappingBookings(booking)) {
            throw new IllegalStateException("there are overlapping bookings");
            //return; // Todo throw Business Exceptios
        }
        em.persist(booking);
    }

    public Optional<Booking> read(String id) {
        return Optional.ofNullable(em.find(Booking.class, id));
    }

    public Booking update(Booking booking) {
        ensureEditRights(booking);

        // TODO validate person needs to be in project

        if (hasOverlappingBookings(booking)) {
            //return; // Todo throw Business Exceptios
        }
        return em.merge(booking);
    }

    public void delete(String bookingId) {
        read(bookingId).ifPresent(em::remove);
    }

    public List<Booking> listMine() {
        String principalName = context.getCallerPrincipal().getName();
        Optional<Person> person = personService.getByPrincipalName(principalName);
        if (person.isPresent()) {
            return em.createNamedQuery(Booking.QUERY_FIND_ALL_FOR_USER, Booking.class).
                setParameter("user", person.get()).
                getResultList();
        }
        return Collections.emptyList();
    }

    private boolean hasOverlappingBookings(Booking booking) {
        return !em.createNamedQuery(Booking.QUERY_FIND_OVERLAPPING)
            .setParameter("startDate", booking.getStart())
            .setParameter("endDate", booking.getEnd())
                .getResultList()
                .isEmpty();
        // TODO use count in DB
    }

    private void ensureEditRights(Booking booking) {
        if (hasOnlyUserRole()) {
            String principalName = context.getCallerPrincipal().getName();
            if (!principalName.equals(booking.getPerson().getName())) {
                // abort because principal with the the only role user can't edit foreign bookings
                throw new EJBAccessException("not allowed to edit foreign bookings");
            }
        }
    }

    private boolean hasOnlyUserRole() {
        return hasRole(Role.USER) && !hasRole(Role.MANAGER) && !hasRole(Role.ADMINISTRATOR);
    }

    private boolean hasRole(Role role) {
        return context.isCallerInRole(role.toString());
    }

    public List<Booking> listAllForUser(String user) {
        return em.createNamedQuery(Booking.QUERY_FIND_ALL_FOR_USERNAME, Booking.class)
            .setParameter("username", user)
            .getResultList();
    }

    @RolesAllowed({"MANAGER", "ADMINISTRATOR"})
    public List<Booking> getAll() {
        return em.createNamedQuery(Booking.QUERY_FIND_ALL, Booking.class).getResultList();
    }
}
