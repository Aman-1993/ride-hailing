package com.uc.rideservice.service;

import com.uc.rideservice.entity.Voucher;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

  @Autowired
  VoucherService voucherService;

  public BigDecimal computeDiscount(BigDecimal actualCost, String voucherCode) {
    Optional<Voucher> voucherOpt = voucherService.getActiveVoucherByCode(voucherCode);
    BigDecimal discount = BigDecimal.ZERO;
    if(voucherOpt.isPresent()) {
      Voucher voucher = voucherOpt.get();
      switch (voucher.getDiscountType()) {
        case FLAT:
          discount = voucher.getDiscount();
          break;
        case PERCENT:
          discount = actualCost.multiply(voucher.getDiscount()).divide(BigDecimal.valueOf(100));
          break;
        default:
          throw new RuntimeException("invalid discount type");
      }
      discount = discount.min(actualCost);
    }
    return discount;
  }
}
