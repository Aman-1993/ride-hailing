package com.uc.rideservice.entity;

import com.uc.rideservice.dto.Location;
import com.uc.rideservice.enums.TripStatus;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Trip {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long driverId;
  private long userId;
  private Integer rating;
  @Enumerated(EnumType.STRING)
  private TripStatus tripStatus;
  private BigDecimal pickupLatitude;
  private BigDecimal pickupLongitude;
  private BigDecimal dropLatitude;
  private BigDecimal dropLongitude;
  private BigDecimal amount;
}
