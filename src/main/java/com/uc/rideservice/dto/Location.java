package com.uc.rideservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Location implements Serializable {
  private static final long serialVersionUID = 3692959421826102727L;

  private BigDecimal latitude;
  private BigDecimal longitude;
}
