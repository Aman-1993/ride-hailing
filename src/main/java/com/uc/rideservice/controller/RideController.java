package com.uc.rideservice.controller;

import com.uc.rideservice.dto.RideConfirmDto;
import com.uc.rideservice.dto.RideRequestDto;
import com.uc.rideservice.dto.TripUpdateDto;
import com.uc.rideservice.entity.Trip;
import com.uc.rideservice.entity.RideRequest;
import com.uc.rideservice.enums.TripStatus;
import com.uc.rideservice.enums.VehicleStatus;
import com.uc.rideservice.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rides")
public class RideController {

  @Autowired
  RideService rideService;

  /**
   * This will request ride, block a fare for some time.
   * Response: fare,
   * @param rideDetails
   */
  @PostMapping("/request")
  public RideRequest requestRide(@RequestBody RideRequestDto rideDetails) {
    return rideService.requestRide(rideDetails);
  }

  @PostMapping
  public Trip bookRide(@RequestBody RideConfirmDto rideConfirmDto) {
    return rideService.confirmRide(rideConfirmDto);
  }

  @PatchMapping("/{id}")
  public Trip updateStatus(@PathVariable long id, @RequestBody TripUpdateDto tripUpdateDto) {
    return rideService.updateStatus(id, tripUpdateDto);
  }
}
