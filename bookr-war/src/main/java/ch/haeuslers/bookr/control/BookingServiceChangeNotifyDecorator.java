package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Booking;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Decorator
public abstract class BookingServiceChangeNotifyDecorator implements BookingService {

    @Inject
    @Delegate
    private transient BookingService bookingService;

    @Inject
    private transient Event<ChangeEvent<Booking>> changeEvent;

    @Override
    public void create(Booking booking) {
        bookingService.create(booking);
        changeEvent.fire(new ChangeEvent<>(Booking.class, booking.getId(), ChangeEvent.ChangeType.CREATE));
    }

    @Override
    public Booking update(Booking booking) {
        changeEvent.fire(new ChangeEvent<>(Booking.class, booking.getId(), ChangeEvent.ChangeType.UPDATE));
        return bookingService.update(booking);
    }

    @Override
    public void delete(String entityId) {
        bookingService.delete(entityId);
        changeEvent.fire(new ChangeEvent<>(Booking.class, entityId, ChangeEvent.ChangeType.DELETE));
    }
}
