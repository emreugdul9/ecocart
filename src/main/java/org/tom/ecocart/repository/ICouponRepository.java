package org.tom.ecocart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tom.ecocart.Entities.Coupon;

public interface ICouponRepository extends JpaRepository<Coupon, String> {

}
