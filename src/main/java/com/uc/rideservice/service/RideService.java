package com.uc.rideservice.service;

import com.uc.rideservice.dto.Location;
import com.uc.rideservice.dto.RideConfirmDto;
import com.uc.rideservice.dto.RideRequestDto;
import com.uc.rideservice.entity.Driver;
import com.uc.rideservice.entity.Trip;
import com.uc.rideservice.entity.RideRequest;
import com.uc.rideservice.entity.Vehicle;
import com.uc.rideservice.enums.TripStatus;
import com.uc.rideservice.enums.UserType;
import com.uc.rideservice.enums.VehicleCategory;
import com.uc.rideservice.enums.VehicleStatus;
import com.uc.rideservice.repo.RideRepo;
import com.uc.rideservice.repo.RideRequestRepo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RideService {

  @Autowired
  PaymentService paymentService;

  @Autowired
  DriverService driverService;

  @Autowired
  RideRequestRepo rideRequestRepo;

  @Autowired
  IVehicleFactory vehicleFactory;

  @Autowired
  RideRepo rideRepo;

  @Value("${ride-request-expiry-in-minutes}")
  private long rideRequestExpiry;

  public RideRequest requestRide(RideRequestDto rideRequestDto) {
    try {
      VehicleCategory requestedCategory = rideRequestDto.getVehicleCategory();
      Vehicle vehicle = vehicleFactory.getVehicleService(rideRequestDto.getVehicleType())
          .getAvailableVehicle(rideRequestDto.getVehicleCategory(), rideRequestDto.getPickup());
      BigDecimal fare = paymentService.getFare(rideRequestDto.getPickup(), rideRequestDto.getDrop(),
          requestedCategory, rideRequestDto.getVoucherCode());
      Driver driver = driverService.getDriverByVehicleId(vehicle.getId());

      //TODO: MapStruct
      RideRequest rideRequest = RideRequest.builder().amount(fare)
          .pickupLatitude(rideRequestDto.getPickup().getLatitude())
          .pickupLongitude(rideRequestDto.getPickup().getLongitude())
          .dropLatitude(rideRequestDto.getDrop().getLatitude())
          .dropLongitude(rideRequestDto.getDrop().getLongitude())
          .vehicleCategory(requestedCategory)
          .passengers(rideRequestDto.getPassengers())
          .userId(rideRequestDto.getUserId())
          .driverId(driver.getId())
          .expiry(LocalDateTime.now().plusMinutes(getRideRequestExpiryInMinutes()))
          .build();

      return rideRequestRepo.save(rideRequest);
    } catch (Exception ex) {
      throw new RuntimeException("No car available");
    }
  }

  public Trip confirmRide(RideConfirmDto rideConfirmDto) {
    long rideRequestId = rideConfirmDto.getRideRequestId();
    Optional<RideRequest> rideRequestOpt = rideRequestRepo.findById(rideRequestId);
    if(rideRequestOpt.isEmpty() || LocalDateTime.now().isAfter(rideRequestOpt.get().getExpiry())) {
      throw new RuntimeException("Ride Request does not exist or is expired. Please generate a new one");
    }
    RideRequest rideRequest = rideRequestOpt.get();

    Trip trip = Trip.builder()
        .tripStatus(TripStatus.IN_PROGRESS)
        .amount(rideRequest.getAmount())
        .pickupLatitude(rideRequest.getPickupLatitude())
        .pickupLongitude(rideRequest.getPickupLongitude())
        .dropLatitude(rideRequest.getDropLatitude())
        .dropLongitude(rideRequest.getPickupLongitude())
        .userId(rideRequest.getUserId())
        .driverId(rideRequest.getDriverId())
        .build();

    Driver driver = getAvailableDriverForRideRequest(rideRequest);
    driverService.updateStatus(driver.getId(), VehicleStatus.TRIP_IN_PROGRESS);
    return rideRepo.save(trip);
  }

  public Trip completeRide(long tripId) {
    Trip trip = getById(tripId);
    long driverId = trip.getDriverId();
    Driver driver = driverService.getDriverById(driverId);
    Vehicle vehicle = vehicleFactory.getVehicleService(driver.getVehicleType()).getVehicleById(driver.getVehicleId());
    vehicle.setStatus(VehicleStatus.AVAILABLE);
    return updateStatus(tripId, TripStatus.COMPLETED);
  }

  private Trip updateStatus(long tripId, TripStatus tripStatus) {
    Trip trip = getById(tripId);
    trip.setTripStatus(tripStatus);
    return rideRepo.save(trip);
  }

  public List<Trip> getCompletedRides(UserType userType, long id) {
    switch (userType) {
      case RIDER:
        return getRidesHistoryOfRider(id);
      case DRIVER:
        return getRidesHistoryOfDriver(id);
      default:
        throw new RuntimeException("Invalid input");
    }
  }

  private List<Trip> getRidesHistoryOfDriver(long driverId) {
    return rideRepo.getAllByDriverIdAndTripStatusIn(driverId, List.of(TripStatus.COMPLETED, TripStatus.IN_PROGRESS));
  }

  private List<Trip> getRidesHistoryOfRider(long userId) {
    return rideRepo.getAllByUserIdAndTripStatusIn(userId, List.of(TripStatus.COMPLETED, TripStatus.IN_PROGRESS));
  }

  private Driver getAvailableDriverForRideRequest(RideRequest rideRequest) {
    Driver driver = driverService.getDriverById(rideRequest.getDriverId());
    Vehicle vehicle = vehicleFactory.getVehicleService(driver.getVehicleType())
        .getVehicleById(driver.getVehicleId());
    if(!vehicle.getStatus().equals(VehicleStatus.AVAILABLE)) {
      vehicle = vehicleFactory.getVehicleService(driver.getVehicleType())
          .getAvailableVehicle(rideRequest.getVehicleCategory(),
              new Location(rideRequest.getPickupLatitude(), rideRequest.getPickupLongitude()));
      driver = driverService.getDriverByVehicleId(vehicle.getId());
    }
    return driver;
  }

  private Trip getById(long id) {
    Optional<Trip> trip = rideRepo.getById(id);
    if(trip.isEmpty()) {
      throw new RuntimeException("Invalid trip Id");
    }
    return trip.get();
  }

  private long getRideRequestExpiryInMinutes() {
    return rideRequestExpiry;
  }

}
