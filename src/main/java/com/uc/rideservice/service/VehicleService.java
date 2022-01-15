package com.uc.rideservice.service;

import com.uc.rideservice.dto.Location;
import com.uc.rideservice.dto.VehicleDto;
import com.uc.rideservice.entity.Car;
import com.uc.rideservice.entity.Vehicle;
import com.uc.rideservice.enums.VehicleCategory;
import com.uc.rideservice.mapper.VehicleMapper;
import com.uc.rideservice.repo.CarRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

  @Autowired
  private VehicleMapper vehicleMapper;

  @Autowired
  private CarRepo carRepo;

  public Vehicle register(VehicleDto vehicleDto) {
    Vehicle vehicle = vehicleMapper.toEntity(vehicleDto);
    return carRepo.save(vehicle);
  }

//  public Car getAvailableVehicle(VehicleCategory category, Location pickup) {
//
//    Optional<Car> car = getAvailableVehicleForCategory(category, pickup);
//    if(car.isEmpty()) {
//      Optional<VehicleCategory> upgradedCategory = upgradeCategory(category);
//      if(upgradedCategory.isPresent()) {
//        car = getAvailableVehicleForCategory(upgradedCategory.get(), pickup);
//      }
//    }
//
//    if(car.isEmpty()) {
//      throw new RuntimeException("No car available");
//    }
//    return car.get();
//  }

  public Vehicle getVehicleById(long id) {
    Optional<Vehicle> vehicle = carRepo.getById(id);
    if(vehicle.isEmpty()) {
      throw new RuntimeException("Vehicle not found");
    }
    return vehicle.get();
  }

  public Vehicle saveOrUpdate(Vehicle vehicle) {
    return carRepo.save(vehicle);
  }

}
