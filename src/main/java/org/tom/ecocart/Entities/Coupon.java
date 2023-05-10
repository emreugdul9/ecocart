package org.tom.ecocart.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "coupons")
public class Coupon {
    @Id
    private String couponId;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private int value;

}
