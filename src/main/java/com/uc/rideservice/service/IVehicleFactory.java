package com.uc.rideservice.service;

import com.uc.rideservice.enums.VehicleType;

public interface IVehicleFactory {
  IVehicleService getVehicleService(VehicleType vehicleType);
}
