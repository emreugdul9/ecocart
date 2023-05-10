package org.tom.ecocart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tom.ecocart.Entities.ShoppingCart;

import java.util.Optional;

public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM shopping_carts WHERE shopping_carts.user_id = ?1 and shopping_carts.is_open=true")
    Optional<ShoppingCart> findShoppingCartByUserAndOpenTrue(String userId);

}
