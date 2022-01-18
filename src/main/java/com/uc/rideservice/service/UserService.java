package com.uc.rideservice.service;

import com.uc.rideservice.dto.UserDto;
import com.uc.rideservice.entity.Trip;
import com.uc.rideservice.entity.Users;
import com.uc.rideservice.mapper.UserMapper;
import com.uc.rideservice.repo.UserRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserRepo userRepo;

  public Users registerUser(UserDto userDto) {
    Users user = userMapper.toEntity(userDto);
    return userRepo.save(user);
  }
}
