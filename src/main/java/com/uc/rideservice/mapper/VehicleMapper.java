package com.uc.rideservice.mapper;

import com.uc.rideservice.dto.UserDto;
import com.uc.rideservice.dto.VehicleDto;
import com.uc.rideservice.entity.Car;
import com.uc.rideservice.entity.Users;
import com.uc.rideservice.entity.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
  Car toEntity(VehicleDto vehicleDto);
  //VehicleDto toDto(Vehicle vehicle);
}
