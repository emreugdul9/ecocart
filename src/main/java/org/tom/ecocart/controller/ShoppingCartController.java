package org.tom.ecocart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tom.ecocart.dto.Product.ProductRequestDTO;
import org.tom.ecocart.dto.ShoppingCart.ShoppingCartDTO;
import org.tom.ecocart.service.ShoppingCartService;

@RestController
@RequestMapping("/api/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PostMapping("/{userId}")
    public ResponseEntity<ShoppingCartDTO> addAProductIntoShoppingCart(@PathVariable String userId, @RequestBody ProductRequestDTO productRequestDTO) throws Exception {
        return new ResponseEntity<>(shoppingCartService.addAProductIntoShoppingCart(userId,productRequestDTO), HttpStatus.OK);
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<ShoppingCartDTO> removeAProductIntoShoppingCart(@PathVariable String userId,@RequestBody ProductRequestDTO productRequestDTO) throws Exception{
        return new ResponseEntity<>(shoppingCartService.removeAProductIntoShoppingCart(userId,productRequestDTO),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCartByUserId(@PathVariable String userId) throws Exception {
        return new ResponseEntity<>(shoppingCartService.getShoppingCartByUserId(userId),HttpStatus.OK);
    }
}
