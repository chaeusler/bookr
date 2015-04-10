package ch.haeuslers.bookr.bondary.rest.v1;

import ch.haeuslers.bookr.control.BookingService;
import ch.haeuslers.bookr.entity.Booking;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("v1/bookings")
@Consumes({"application/json", "application/xml"})
@Produces({"application/json", "application/xml"})
@RolesAllowed({"USER", "MANAGER", "ADMINISTRATOR"})
@DeclareRoles({"USER", "MANAGER", "ADMINISTRATOR"})
public class BookingResource {

    @Inject
    BookingService bookingService;

    @PUT
    public Booking create(Booking booking) throws IllegalAccessException {
       return bookingService.persist(booking);
    }

    @POST
    public Booking update(Booking booking) throws IllegalAccessException {
        return bookingService.merge(booking);
    }

    @GET
    public List<Booking> listMine() {
        return bookingService.listMine();
    }
}
