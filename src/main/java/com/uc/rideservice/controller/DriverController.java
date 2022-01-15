package com.uc.rideservice.controller;

import com.uc.rideservice.dto.DriverDto;
import com.uc.rideservice.dto.Location;
import com.uc.rideservice.entity.Driver;
import com.uc.rideservice.enums.VehicleStatus;
import com.uc.rideservice.service.DriverService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drivers")
public class DriverController {

  @Autowired
  DriverService driverService;

  @PostMapping
  public Driver register(@RequestBody @Valid DriverDto driverDto) {
    return driverService.register(driverDto);
  }

  @PatchMapping("/{id}/location")
  public Location updateLocation(@RequestBody Location location, @PathVariable long id) {
    return driverService.updateDriverLocation(id, location);
  }

  @PatchMapping("/{id}")
  public VehicleStatus updateStatus(@PathVariable long id, @RequestBody VehicleStatus status) {
    return driverService.updateStatus(id, status);
  }
}
