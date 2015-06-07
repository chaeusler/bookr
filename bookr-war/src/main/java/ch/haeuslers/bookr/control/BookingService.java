package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Booking;

import java.util.List;

public interface BookingService extends CRUDService<Booking> {
    List<Booking> listMine();
}
