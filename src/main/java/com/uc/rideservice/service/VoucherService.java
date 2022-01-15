package com.uc.rideservice.service;

import com.uc.rideservice.dto.VoucherDto;
import com.uc.rideservice.entity.Voucher;
import com.uc.rideservice.enums.VoucherStatus;
import com.uc.rideservice.mapper.VoucherMapper;
import com.uc.rideservice.repo.VoucherRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

  @Autowired
  private VoucherRepo voucherRepo;

  @Autowired
  private VoucherMapper voucherMapper;

  public Voucher createVoucher(VoucherDto voucherDto) {
    Voucher voucher = voucherMapper.toEntity(voucherDto);
    return voucherRepo.save(voucher);
  }

  public Voucher deleteVoucher(long voucherId) {
    Voucher voucher = getVoucherById(voucherId);
    voucher.setStatus(VoucherStatus.INACTIVE);
    return voucherRepo.save(voucher);
  }

  public Optional<Voucher> getVoucherByCode(String voucherCode) {
    return voucherRepo.getByCode(voucherCode);
  }

  private Voucher getVoucherById(long voucherId) {
    Optional<Voucher> voucher = voucherRepo.getById(voucherId);
    if (voucher.isEmpty()) {
      throw new RuntimeException("voucher id does not exist");
    }
    return voucher.get();
  }
}
