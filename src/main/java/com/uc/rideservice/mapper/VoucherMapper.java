package com.uc.rideservice.mapper;

import com.uc.rideservice.dto.VoucherDto;
import com.uc.rideservice.entity.Voucher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
  Voucher toEntity(VoucherDto voucherDto);
}
