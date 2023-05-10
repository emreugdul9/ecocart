package org.tom.ecocart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tom.ecocart.Entities.Coupon;
import org.tom.ecocart.Entities.DiscountType;
import org.tom.ecocart.Entities.ShoppingCart;
import org.tom.ecocart.dto.ShoppingCart.ShoppingCartDTO;
import org.tom.ecocart.repository.ICouponRepository;
import org.tom.ecocart.repository.IShoppingCartRepository;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final ICouponRepository couponRepository;

    private final IShoppingCartRepository shoppingCartRepository;

    public ShoppingCartDTO applyACouponToShoppingCart(String userId, String coupon) throws Exception {
        Coupon optionalCoupon = couponRepository.findById(coupon).orElseThrow(() -> new Exception("Coupon not found"));
        ShoppingCart optionalShoppingCart = shoppingCartRepository.findShoppingCartByUserAndOpenTrue(userId).orElseThrow(()-> new Exception("Open shopping cart not found"));
        optionalShoppingCart.setCoupon(optionalCoupon);
        if (optionalCoupon.getDiscountType().equals(DiscountType.Amount)){
            optionalShoppingCart.setDiscountPrice(optionalShoppingCart.getTotalPrice() - optionalCoupon.getValue());
        } else {
            double totalPrice = optionalShoppingCart.getTotalPrice();
            double discountValue = totalPrice - (totalPrice * optionalCoupon.getValue()) / 100;
            optionalShoppingCart.setDiscountPrice(discountValue);
        }
        shoppingCartRepository.save(optionalShoppingCart);
        return ShoppingCartService.getShoppingCartDTO(optionalShoppingCart);


    }
}
