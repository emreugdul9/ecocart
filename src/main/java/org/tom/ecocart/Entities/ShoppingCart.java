package org.tom.ecocart.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private double totalPrice;
    @ManyToMany
    private List<Product> products;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    private boolean isOpen;
    @ManyToOne
    private Coupon coupon;
    private double discountPrice;
}
