package com.uc.rideservice.entity;

import com.uc.rideservice.enums.VehicleCategory;
import com.uc.rideservice.enums.VehicleStatus;
import java.math.BigDecimal;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Vehicle {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String regNo;
  @Enumerated(EnumType.STRING)
  private VehicleStatus status;
  private BigDecimal latitude;
  private BigDecimal longitude;
  @Enumerated(EnumType.STRING)
  private VehicleCategory category;
}