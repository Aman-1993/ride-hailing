package com.uc.rideservice.service;

import com.uc.rideservice.dto.RideConfirmDto;
import com.uc.rideservice.dto.RideRequestDto;
import com.uc.rideservice.dto.TripUpdateDto;
import com.uc.rideservice.entity.Driver;
import com.uc.rideservice.entity.Trip;
import com.uc.rideservice.entity.RideRequest;
import com.uc.rideservice.entity.Vehicle;
import com.uc.rideservice.enums.TripStatus;
import com.uc.rideservice.enums.VehicleCategory;
import com.uc.rideservice.repo.RideRepo;
import com.uc.rideservice.repo.RideRequestRepo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
      VehicleCategory originalCategory = rideRequestDto.getVehicleCategory();
      Vehicle vehicle = vehicleFactory.getVehicleService(rideRequestDto.getVehicleType())
          .getAvailableVehicle(rideRequestDto.getVehicleType(), rideRequestDto.getVehicleCategory(), rideRequestDto.getPickup());
      BigDecimal fare = paymentService.getFare(rideRequestDto.getPickup(), rideRequestDto.getDrop(),
          originalCategory, rideRequestDto.getVoucherCode());
      Driver driver = driverService.getDriverByVehicleId(vehicle.getId());

      //TODO: MapStruct
      RideRequest rideRequest = RideRequest.builder().amount(fare)
          .pickupLatitude(rideRequestDto.getPickup().getLatitude())
          .pickupLongitude(rideRequestDto.getPickup().getLongitude())
          .dropLatitude(rideRequestDto.getDrop().getLatitude())
          .dropLongitude(rideRequestDto.getDrop().getLongitude())
          .vehicleCategory(vehicle.getCategory())
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
        .tripStatus(TripStatus.BOOKED)
        .amount(rideRequest.getAmount())
        .pickupLatitude(rideRequest.getPickupLatitude())
        .pickupLongitude(rideRequest.getPickupLongitude())
        .dropLatitude(rideRequest.getDropLatitude())
        .dropLongitude(rideRequest.getPickupLongitude())
        .userId(rideRequest.getUserId())
        .driverId(rideRequest.getDriverId())
        .build();

    return rideRepo.save(trip);
  }

  public Trip updateStatus(long tripId, TripUpdateDto tripUpdateDto) {
    Trip trip = getById(tripId);
    trip.setTripStatus(tripUpdateDto.getTripStatus());
    return rideRepo.save(trip);
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
