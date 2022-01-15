package com.uc.rideservice.dto;

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
public class DriverDto extends PersonDto {
  private long id;
  private VehicleDto vehicle;
  private String license;
  @Enumerated(EnumType.STRING)
  private VehicleType vehicleType = VehicleType.CAR;
}
