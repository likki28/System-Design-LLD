package ParkingLotDesign.controllers;

import ParkingLotDesign.Entities.ParkingSlot;
import ParkingLotDesign.Entities.Vehicle;
import ParkingLotDesign.services.SlotService;

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



    public EntryController(SlotService slotservice) {
        this.slotservice = slotservice;
    }

    public void EnterVehicle(String licensePlate, Vehicle.VehicleType vehicleType){

        Vehicle vehicle=new Vehicle(licensePlate,vehicleType);


        Optional<UUID> slotId= slotservice.allocateSlot(vehicle.getVehicleType())
                .map(slot -> slot.getId());;










    }
}
