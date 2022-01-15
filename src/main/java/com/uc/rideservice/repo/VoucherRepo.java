package com.uc.rideservice.repo;

import com.uc.rideservice.entity.Voucher;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepo extends JpaRepository<Voucher, Long> {
  Optional<Voucher> getById(long id);
  Optional<Voucher> getByCode(String code);
}
