package com.uc.rideservice.repo;

import com.uc.rideservice.entity.Trip;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepo extends JpaRepository<Trip, Long> {
  Optional<Trip> getById(long id);
}
