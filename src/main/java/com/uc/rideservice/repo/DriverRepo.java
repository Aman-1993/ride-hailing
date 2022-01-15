package com.uc.rideservice.repo;

import com.uc.rideservice.entity.Driver;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {
  Optional<Driver> getById(long id);
  Optional<Driver> getByVehicleId(long vehicleId);
}
