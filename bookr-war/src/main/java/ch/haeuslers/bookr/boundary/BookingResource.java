package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.BookingService;
import ch.haeuslers.bookr.entity.Booking;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("v1/bookings")
public class BookingResource {

    @Inject
    BookingService bookingService;

    @GET
    @Produces({"application/json", "application/xml"})
    public List<Booking> getAll() {
        return bookingService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces({"application/json", "application/xml"})
    public Booking read(@PathParam("id") String bookingId) {
        return bookingService.read(bookingId).orElseThrow(NotFoundException::new);
    }

    @POST
    @Path("{id}")
    @Consumes({"application/json", "application/xml"})
    public void create(@PathParam("id") String bookingId, Booking booking) {
        // TODO verify id
        bookingService.create(booking);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json", "application/xml"})
    public void update(@PathParam("id") String bookingId, Booking booking) {
        // TODO verify id
        bookingService.update(booking);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        bookingService.delete(id);
    }
}
