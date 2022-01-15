package com.uc.rideservice.repo;

import com.uc.rideservice.entity.Car;
import com.uc.rideservice.entity.Vehicle;
import com.uc.rideservice.enums.VehicleCategory;
import com.uc.rideservice.enums.VehicleStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepo extends JpaRepository<Car, Integer> {

  Optional<Vehicle> getById(long id);
  List<Vehicle> getAllByCategoryAndStatusAndLatitudeBetweenAndLongitudeBetween(
      VehicleCategory category, VehicleStatus status,
      BigDecimal startLat, BigDecimal endLat, BigDecimal startLong, BigDecimal endLong);
  Vehicle save(Vehicle vehicle);
}
