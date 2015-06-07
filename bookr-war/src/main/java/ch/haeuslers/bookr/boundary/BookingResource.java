package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.BookingServiceBean;
import ch.haeuslers.bookr.entity.Booking;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("v1/bookings")
public class BookingResource {

    public static final String PATH_ID = "{id}";
    public static final String PATH_PARAM_ID = "id";

    @Inject
    private transient BookingServiceBean bookingService;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Booking> getAll() {
        return bookingService.getAll();
    }

    @GET
    @Path(PATH_ID)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Booking read(final @PathParam(PATH_PARAM_ID) String bookingId) {
        return bookingService.read(bookingId).orElseThrow(NotFoundException::new);
    }

    @POST
    @Path(PATH_ID)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void create(final @PathParam(PATH_PARAM_ID) String bookingId, final Booking booking) {
        // TODO verify id
        bookingService.create(booking);
    }

    @PUT
    @Path(PATH_ID)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void update(final @PathParam(PATH_PARAM_ID) String bookingId, final Booking booking) {
        // TODO verify id
        bookingService.update(booking);
    }

    @DELETE
    @Path("{id}")
    public void delete(final @PathParam(PATH_PARAM_ID) String id) {
        bookingService.delete(id);
    }
}
