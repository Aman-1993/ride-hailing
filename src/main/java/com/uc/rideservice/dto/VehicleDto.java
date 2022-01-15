package com.uc.rideservice.dto;

import com.uc.rideservice.enums.VehicleCategory;
import com.uc.rideservice.enums.VehicleStatus;
import com.uc.rideservice.enums.VehicleType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleDto {
  private VehicleType type;
  private String regNo;
  private String make;
  private String model;
  private VehicleCategory category;
  @Enumerated(EnumType.STRING)
  private VehicleStatus status= VehicleStatus.OFFLINE;
}