package com.uc.rideservice.service;

import com.uc.rideservice.enums.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleFactory implements IVehicleFactory{

  @Autowired
  CarService carService;

  @Override
  public IVehicleService getVehicleService(VehicleType vehicleType) {
    switch (vehicleType) {
      case CAR:
        return carService;
      default:
        throw new RuntimeException("Service does not exist");
    }
  }
}
