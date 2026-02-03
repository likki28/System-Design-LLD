package ParkingLotDesign.repository;

import ParkingLotDesign.domain.PricingRule;
import ParkingLotDesign.domain.Vehicle;

import java.util.Optional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PricingRuleRepository {
    private Map<UUID, PricingRule> rules = new ConcurrentHashMap<>();
    private Map<Vehicle.VehicleType, UUID> vehicleTypeToRule = new ConcurrentHashMap<>();


    public Optional<PricingRule> findByVehicleType(Vehicle.VehicleType vehicleType) {
        UUID ruleId=vehicleTypeToRule.get(vehicleType);
        return ruleId !=null ? Optional.ofNullable(rules.get(ruleId)) : Optional.empty();
    }
}
