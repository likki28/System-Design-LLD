package ParkingLotDesign.repository;

import ParkingLotDesign.Entities.Ticket;

import java.util.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TicketRepository {
    private Map<UUID, Ticket> tickets = new ConcurrentHashMap<>();

    public Ticket save(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
        return ticket;
    }
}
