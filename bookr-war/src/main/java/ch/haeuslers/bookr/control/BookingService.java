package ch.haeuslers.bookr.control;

import ch.haeuslers.bookr.entity.Booking;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import java.util.List;


@DeclareRoles({"USER", "MANAGER", "ADMINISTRATOR"})
@RolesAllowed({"USER", "MANAGER", "ADMINISTRATOR"})
public interface BookingService extends CRUDService<Booking> {

    List<Booking> listMine();

    @RolesAllowed({"MANAGER", "ADMINISTRATOR"})
    List<Booking> getAll();
}
