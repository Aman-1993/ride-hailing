package com.uc.rideservice.mapper;

import com.uc.rideservice.dto.UserDto;
import com.uc.rideservice.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  Users toEntity(UserDto userDto);
  UserDto toDto(Users users);
}
