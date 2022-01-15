package com.uc.rideservice.dto;

import com.uc.rideservice.enums.TripStatus;
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
public class TripUpdateDto {
  @Enumerated(EnumType.STRING)
  private TripStatus tripStatus;
}
