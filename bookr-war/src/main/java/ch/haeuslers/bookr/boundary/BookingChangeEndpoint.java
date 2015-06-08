package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.ChangeEvent;
import ch.haeuslers.bookr.entity.Booking;

import javax.enterprise.event.Observes;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/ws/bookingChange")
public class BookingChangeEndpoint {

    private transient Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    void receive(@Observes ChangeEvent<Booking> bookingChangeEvent) throws IOException {
        if (session != null && session.isOpen()) {
            session.getBasicRemote().sendText(bookingChangeEvent.toString());
        }
    }
}
