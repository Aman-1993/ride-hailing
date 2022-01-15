package com.uc.rideservice.entity;

import com.uc.rideservice.enums.VehicleType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Driver extends Person{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long vehicleId;
  private String license;
  @Enumerated(EnumType.STRING)
  private VehicleType vehicleType;
}
