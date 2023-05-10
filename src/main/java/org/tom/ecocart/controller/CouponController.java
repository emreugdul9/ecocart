package org.tom.ecocart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tom.ecocart.dto.ShoppingCart.ShoppingCartDTO;
import org.tom.ecocart.service.CouponService;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    @PatchMapping("/{userId}")
    public ResponseEntity<ShoppingCartDTO> applyACouponToShoppingCart(@PathVariable String userId, @RequestParam(value = "couponId") String couponId) throws Exception {
        return new ResponseEntity<>(couponService.applyACouponToShoppingCart(userId,couponId), HttpStatus.OK);
    }
}
