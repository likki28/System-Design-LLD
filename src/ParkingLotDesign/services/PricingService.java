package ParkingLotDesign.services;

import ParkingLotDesign.domain.PricingRule;
import ParkingLotDesign.domain.Ticket;
import ParkingLotDesign.domain.Vehicle;
import ParkingLotDesign.repository.PricingRuleRepository;

import java.util.Optional;

public class PricingService {
    private PricingRuleRepository pricingRuleRepository;

    public PricingService(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }

    public double calculateFee(Ticket ticket) {

        System.out.println("[SERVICE] Calculating fee for ticket: " + ticket.getId());

        Vehicle.VehicleType vehicleType = Vehicle.VehicleType.CAR;

        Optional<PricingRule> rule = pricingRuleRepository.findByVehicleType(vehicleType);

        if (rule.isEmpty()) {
            throw new IllegalStateException("No rule found for vehicle type");
        }

        PricingRule pricingRule = rule.get();

        double flatFee = pricingRule.getFlatRate();
        double hourlyFee = calculateHourlyFee(ticket, pricingRule.getRatePerHour());

        double finalFee = Math.min(flatFee, hourlyFee);

        System.out.println(
                "[SERVICE] Flat fee: " + flatFee + ", Hourly fee: " + hourlyFee + ", Final fee: " + finalFee + " for vehicle type: " + vehicleType);

        return finalFee;


    }

    private double calculateHourlyFee(Ticket ticket, double ratePerHour) {
        java.time.Duration duration = java.time.Duration.between(ticket.getEntryTime(), java.time.LocalDate.now());
        long hours=duration.toHours();
        if(hours<1){
            hours=1;
        }
        return hours*ratePerHour;
    }
}