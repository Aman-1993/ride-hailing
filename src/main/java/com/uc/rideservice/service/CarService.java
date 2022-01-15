package com.uc.rideservice.service;

import com.uc.rideservice.entity.Vehicle;
import com.uc.rideservice.enums.VehicleStatus;
import com.uc.rideservice.enums.VehicleCategory;
import com.uc.rideservice.repo.CarRepo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService extends AbstractVehicleService{

  @Autowired
  CarRepo carRepo;

  @Override
  public List<Vehicle> getAllByCategoryStatusLatitudeAndLongitude(VehicleCategory vehicleCategory,
      VehicleStatus status, BigDecimal latStart, BigDecimal latEnd, BigDecimal longStart, BigDecimal longEnd) {
    return carRepo.getAllByCategoryAndStatusAndLatitudeBetweenAndLongitudeBetween(
        vehicleCategory, VehicleStatus.AVAILABLE, latStart, latEnd, longStart, longEnd);
  }

  @Override
  public Optional<VehicleCategory> upgradeCategory(VehicleCategory carCategory) {
    switch (carCategory) {
      case HATCHBACK:
        return Optional.of(VehicleCategory.SEDAN);
      default:
        return Optional.empty();
    }
  }


}
