package com.uc.rideservice.mapper;

import com.uc.rideservice.dto.VehicleDto;
import com.uc.rideservice.entity.Car;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
  Car toCarEntity(VehicleDto vehicleDto);
}
