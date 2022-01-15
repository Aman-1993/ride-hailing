package com.uc.rideservice.service;

import com.uc.rideservice.dto.Location;
import com.uc.rideservice.entity.Vehicle;
import com.uc.rideservice.enums.VehicleCategory;
import com.uc.rideservice.enums.VehicleStatus;
import com.uc.rideservice.enums.VehicleType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractVehicleService implements IVehicleService{

  @Value("${ride.search.threshold.radius}")
  private BigDecimal thresholdRadius;

  public Vehicle getAvailableVehicle(VehicleType vehicleType, VehicleCategory category, Location pickup) {

    Optional<Vehicle> vehicle = getAvailableVehicleForCategory(category, pickup);
    if(vehicle.isEmpty()) {
      Optional<VehicleCategory> upgradedCategory = upgradeCategory(category);
      if(upgradedCategory.isPresent()) {
        vehicle = getAvailableVehicleForCategory(upgradedCategory.get(), pickup);
      }
    }

    if(vehicle.isEmpty()) {
      throw new RuntimeException("No vehicle available");
    }
    return vehicle.get();
  }

  private Optional<Vehicle> getAvailableVehicleForCategory(VehicleCategory category, Location pickup) {

    BigDecimal pickupLat = pickup.getLatitude();
    BigDecimal pickupLong = pickup.getLongitude();

    List<Vehicle> vehicles = getAllByCategoryStatusLatitudeAndLongitude(category, VehicleStatus.AVAILABLE,
            pickupLat.subtract(thresholdRadius), pickupLat.add(thresholdRadius),
        pickupLong.subtract(thresholdRadius), pickupLong.add(thresholdRadius));

    return vehicles.stream().min((vehicle1, vehicle2) ->
        (getDistance(vehicle1.getLatitude(), vehicle1.getLongitude(), pickupLat, pickupLong).subtract(
            getDistance(vehicle2.getLatitude(), vehicle2.getLongitude(), pickupLat, pickupLong)) ).intValue());
  }

  private BigDecimal getDistance(BigDecimal lat1, BigDecimal long1, BigDecimal lat2, BigDecimal long2) {
    return lat2.subtract(lat1).abs().add(long2.subtract(long1).abs());
  }
}
