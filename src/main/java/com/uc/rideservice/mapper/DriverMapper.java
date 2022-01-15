package com.uc.rideservice.mapper;

import com.uc.rideservice.dto.DriverDto;
import com.uc.rideservice.entity.Driver;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper {
  Driver toEntity(DriverDto driverDto, long vehicleId);
  DriverDto toDto(Driver driver);
}
