package com.uc.rideservice.controller;

import com.uc.rideservice.dto.UserDto;
import com.uc.rideservice.entity.Users;
import com.uc.rideservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping
  public Users createUser(@RequestBody UserDto user) {
    return userService.registerUser(user);
  }
}
