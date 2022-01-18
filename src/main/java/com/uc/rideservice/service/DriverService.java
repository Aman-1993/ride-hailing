package com.uc.rideservice.service;

import com.uc.rideservice.dto.DriverDto;
import com.uc.rideservice.dto.Location;
import com.uc.rideservice.dto.VehicleDto;
import com.uc.rideservice.entity.Driver;
import com.uc.rideservice.entity.Vehicle;
import com.uc.rideservice.enums.VehicleStatus;
import com.uc.rideservice.mapper.DriverMapper;
import com.uc.rideservice.repo.DriverRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

  @Autowired
  private DriverRepo driverRepo;

  @Autowired
  private DriverMapper driverMapper;

  @Autowired
  private IVehicleFactory vehicleFactory;

  public Driver register(DriverDto driverDto) {
    VehicleDto vehicleDto = driverDto.getVehicle();
    Vehicle vehicle = vehicleFactory.getVehicleService(driverDto.getVehicleType())
        .register(vehicleDto);
    Driver driver = driverMapper.toEntity(driverDto, vehicle.getId());
    return driverRepo.save(driver);
  }

  public Location updateDriverLocation(long driverId, Location location) {
    Driver driver = getDriverById(driverId);
    Vehicle vehicle = vehicleFactory.getVehicleService(driver.getVehicleType())
        .getVehicleById(driver.getVehicleId());
    vehicle.setLatitude(location.getLatitude());
    vehicle.setLongitude(location.getLongitude());
    vehicleFactory.getVehicleService(driver.getVehicleType()).saveOrUpdateVehicle(vehicle);
    return location;
  }

  public Driver getDriverByVehicleId(long vehicleId) {
    Optional<Driver> driver = driverRepo.getByVehicleId(vehicleId);
    if(driver.isEmpty()) {
      throw new RuntimeException("No driver exists with the given vehicle id");
    }
    return driver.get();
  }

  public VehicleStatus updateStatus(long id, VehicleStatus status) {
    Driver driver =getDriverById(id);
    Vehicle vehicle = vehicleFactory.getVehicleService(driver.getVehicleType())
        .getVehicleById(driver.getVehicleId());
    vehicle.setStatus(status);
    vehicleFactory.getVehicleService(driver.getVehicleType()).saveOrUpdateVehicle(vehicle);
    return status;
  }

  public Driver getDriverById(long id) {
    Optional<Driver> driver = driverRepo.getById(id);
    if(driver.isEmpty()) {
      throw new RuntimeException("Invalid driver Id");
    }
    return driver.get();
  }

}
