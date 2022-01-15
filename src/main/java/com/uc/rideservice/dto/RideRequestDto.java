package com.uc.rideservice.dto;

import com.uc.rideservice.enums.VehicleCategory;
import com.uc.rideservice.enums.VehicleType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RideRequestDto {
  private Location pickup;
  private Location drop;
  private int passengers;
  @Enumerated(EnumType.STRING)
  private VehicleCategory vehicleCategory;
  @Enumerated(EnumType.STRING)
  private VehicleType vehicleType = VehicleType.CAR;
  @NotNull
  private long userId;
  private String voucherCode;
}