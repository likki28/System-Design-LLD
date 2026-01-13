import ParkingLotDesign.Entities.Vehicle;
import ParkingLotDesign.controllers.EntryController;
import ParkingLotDesign.services.SlotService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {



        EntryController entrycontroller=new EntryController(new SlotService());

        entrycontroller.EnterVehicle("TS08GD5890", Vehicle.VehicleType.BIKE);
    }
}