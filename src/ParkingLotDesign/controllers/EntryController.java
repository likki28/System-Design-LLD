package ParkingLotDesign.controllers;

import ParkingLotDesign.Entities.ParkingSlot;
import ParkingLotDesign.Entities.Ticket;
import ParkingLotDesign.Entities.Vehicle;
import ParkingLotDesign.services.SlotService;
import ParkingLotDesign.services.TicketService;

import java.util.Optional;
import java.util.UUID;

/*
Vehicle arrives
Slot allocated
Ticket generated
Slot marked as occupied
 */
public class EntryController {

    private SlotService slotservice;
    private TicketService ticketService;


    public EntryController(SlotService slotservice, TicketService ticketService) {
        this.slotservice = slotservice;
        this.ticketService = ticketService;
    }

    public EntryResult EnterVehicle(String licensePlate, Vehicle.VehicleType vehicleType){

        Vehicle vehicle=new Vehicle(licensePlate,vehicleType);


        Optional<UUID> slotId= slotservice.allocateSlot(vehicle.getVehicleType())
                .map(slot -> slot.getId());
        if (slotId.isEmpty()) {
            return new EntryResult(false, null, null, "No available slots for vehicle type: " + vehicleType);
        }

        Ticket ticket=ticketService.generateTicket(vehicle,slotId.get());
        System.out.println("[CONTROLLER] Vehicle entry successful - Ticket: " + ticket.getId() + ", Slot: " + slotId.get());
        return new EntryResult(true, ticket.getId(), slotId.get(), "Entry successful");

    }


    public static class EntryResult {
        private final boolean success;
        private final UUID ticketId;
        private final UUID slotId;
        private final String message;

        public EntryResult(boolean success, UUID ticketId, UUID slotId, String message) {
            this.success = success;
            this.ticketId = ticketId;
            this.slotId = slotId;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public UUID getTicketId() { return ticketId; }
        public UUID getSlotId() { return slotId; }
        public String getMessage() { return message; }
    }
}
