package com.uc.rideservice.repo;

import com.uc.rideservice.entity.Trip;
import com.uc.rideservice.enums.TripStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepo extends JpaRepository<Trip, Long> {
  Optional<Trip> getById(long id);
  List<Trip> getAllByDriverIdAndTripStatusIn(long driverId, List<TripStatus> statuses);
  List<Trip> getAllByUserIdAndTripStatusIn(long userId, List<TripStatus> status);
}
