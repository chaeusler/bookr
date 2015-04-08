package ch.haeuslers.bookr.bondary.rest.v1;

import ch.haeuslers.bookr.control.BookingService;
import ch.haeuslers.bookr.entity.Booking;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("v1/bookings")
@Consumes("application/json")
@Produces("application/json")
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
