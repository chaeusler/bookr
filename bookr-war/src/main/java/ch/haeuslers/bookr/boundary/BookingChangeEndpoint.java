package ch.haeuslers.bookr.boundary;

import ch.haeuslers.bookr.control.ChangeEvent;
import ch.haeuslers.bookr.entity.Booking;

import javax.enterprise.event.Observes;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/ws/bookingChange")
public class BookingChangeEndpoint {

    private static final Set<Session> PEERS = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session peer) {
        PEERS.add(peer);
    }

    @OnClose
    public void onClose(Session peer) {
        PEERS.remove(peer);
    }

    void receive(@Observes ChangeEvent<Booking> bookingChangeEvent) throws IOException {
        for (Session peer : PEERS) {
            if (peer.isOpen()) {
                peer.getBasicRemote().sendText(bookingChangeEvent.toString());
            }
        }
    }
}
