package ParkingLotDesign.services;

import ParkingLotDesign.Entities.Ticket;
import ParkingLotDesign.Entities.Vehicle;
import ParkingLotDesign.repository.TicketRepository;

import java.util.UUID;

public class TicketService {

    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;

    }

    public Ticket generateTicket(Vehicle vehicle, UUID slotId){
        System.out.println("[SERVICE] Generating ticket for vehicle: " + vehicle.getLicensePlate());

        Ticket ticket = new Ticket(vehicle.getId(), slotId);
        ticketRepository.save(ticket);

        System.out.println("[SERVICE] Ticket generated successfully: " + ticket.getId());
        return ticket;



    }
}
