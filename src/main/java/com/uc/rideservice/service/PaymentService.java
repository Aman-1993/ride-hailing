package com.uc.rideservice.service;

import com.uc.rideservice.dto.Location;
import com.uc.rideservice.enums.VehicleCategory;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  @Value("#{${pricing-strategy}}")
  Map<BigDecimal, BigDecimal> pricing;

  @Value("${base-price}")
  BigDecimal basePrice;

  @Value("${base-distance}")
  BigDecimal baseDistance;

  public BigDecimal getFare(Location pickup, Location drop, VehicleCategory carCategory) {
    BigDecimal pickupLat = pickup.getLatitude(), pickupLong = pickup.getLongitude();
    BigDecimal dropLat = drop.getLatitude(), dropLong = drop.getLongitude();
    BigDecimal distance = getDistance(pickupLat, pickupLong, dropLat, dropLong);
    BigDecimal fare = getPrice(distance);
    return fare.multiply(getMultiplier(carCategory, pickup));
  }

  private BigDecimal getDistance(BigDecimal lat1, BigDecimal long1, BigDecimal lat2, BigDecimal long2) {
    return lat2.subtract(lat1).abs().add(long2.subtract(long1).abs());
  }

  private BigDecimal getPrice(BigDecimal distance) {
    BigDecimal cost = basePrice;
    distance = distance.subtract(baseDistance);
    distance = distance.max(BigDecimal.valueOf(0));

    for(Map.Entry<BigDecimal, BigDecimal> entry: pricing.entrySet()) {
      BigDecimal currentDistSlab = entry.getKey(), currentSlabCost = entry.getValue();
      if( distance.compareTo(currentDistSlab) >= 0) {
        cost = cost.add(distance.multiply(currentSlabCost));
        break;
      } else {
        cost = cost.add(currentDistSlab.multiply(currentSlabCost));
        distance = distance.subtract(currentDistSlab);
      }
    }
    return cost;
  }

  private BigDecimal getMultiplier(VehicleCategory carCategory, Location pickup) {
    //location based surge can be added..
    switch (carCategory) {
      case SEDAN:
        return BigDecimal.valueOf(1.2);
      default:
        return BigDecimal.ONE;
    }
  }
}
