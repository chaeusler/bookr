package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Booking;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class BookingChangeEventReceiver {

    private ChangeEvent<Booking> bookingChangeEvent;

    void receive(@Observes ChangeEvent<Booking> bookingChangeEvent) {
        this.bookingChangeEvent = bookingChangeEvent;
    }

    public ChangeEvent<Booking> getBookingChangeEvent() {
        return bookingChangeEvent;
    }
}
