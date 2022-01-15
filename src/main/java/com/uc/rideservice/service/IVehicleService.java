package com.uc.rideservice.service;

import com.uc.rideservice.dto.Location;
import com.uc.rideservice.dto.VehicleDto;
import com.uc.rideservice.entity.Vehicle;
import com.uc.rideservice.enums.VehicleCategory;
import com.uc.rideservice.enums.VehicleStatus;
import com.uc.rideservice.enums.VehicleType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IVehicleService {

  Vehicle register(VehicleDto vehicleDto);
  Vehicle getVehicleById(long id);

  Vehicle saveOrUpdateVehicle(Vehicle vehicle);

  Vehicle getAvailableVehicle(VehicleType vehicleType, VehicleCategory category, Location pickup);

  List<Vehicle> getAllByCategoryStatusLatitudeAndLongitude(VehicleCategory vehicleCategory,
      VehicleStatus status, BigDecimal latitudeStart, BigDecimal latitudeEnd,
      BigDecimal longitudeStart, BigDecimal longitudeEnd);

  Optional<VehicleCategory> upgradeCategory(VehicleCategory carCategory);
}
