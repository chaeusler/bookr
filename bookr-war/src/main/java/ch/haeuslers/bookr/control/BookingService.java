package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Booking;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import java.util.List;

public interface BookingService extends CRUDService<Booking> {

    List<Booking> listMine();

    List<Booking> getAll();
}
