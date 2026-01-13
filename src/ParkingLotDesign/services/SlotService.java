package ParkingLotDesign.services;

import ParkingLotDesign.Entities.ParkingSlot;
import ParkingLotDesign.Entities.Vehicle;
import ParkingLotDesign.repository.SlotRepository;

public class SlotService {

    private SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public Optional<ParkingSlot> allocateSlot(Vehicle.VehicleType vehicleType) {
        System.out.println("[SERVICE] Allocating slot for vehicle type: " + vehicleType);

        Optional<ParkingSlot> slot = slotRepository.allocateSlot(vehicleType);
        if (slot.isPresent()) {
            System.out.println("[SERVICE] Slot allocated successfully: " + slot.get().getId());
        } else {
            System.out.println("[SERVICE] No available slots for vehicle type: " + vehicleType);
        }

        return slot;
    }




}
