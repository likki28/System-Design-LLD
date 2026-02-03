package ParkingLotDesign.repository;

import ParkingLotDesign.domain.Ticket;

import java.util.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TicketRepository {
    private Map<UUID, Ticket> tickets = new ConcurrentHashMap<>();

    public Ticket save(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
        return ticket;
    }

    public Optional<Ticket> findById(UUID ticketId) {
        return Optional.ofNullable(tickets.get(ticketId));
    }

    public void deactivateTicket(UUID ticketId) {
        tickets.computeIfPresent(ticketId, (id, ticket) -> {
            ticket.deactivate();
            return ticket;
        });
    }
}
