package com.uc.rideservice.controller;

import com.uc.rideservice.dto.VoucherDto;
import com.uc.rideservice.entity.Voucher;
import com.uc.rideservice.enums.VoucherStatus;
import com.uc.rideservice.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

  @Autowired
  VoucherService voucherService;

  @PostMapping
  public Voucher createVoucher(@RequestBody VoucherDto voucherDto) {
    return voucherService.createVoucher(voucherDto);
  }

  @DeleteMapping("/{id}")
  public Voucher deleteVoucher(@PathVariable long id) {
    return voucherService.deleteVoucher(id);
  }
}
