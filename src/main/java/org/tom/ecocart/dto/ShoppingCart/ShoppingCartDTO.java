package org.tom.ecocart.dto.ShoppingCart;

import lombok.Getter;
import lombok.Setter;
import org.tom.ecocart.dto.Product.ProductResponseDTO;

import java.util.List;

@Getter
@Setter
public class ShoppingCartDTO {
    private String id;
    private List<ProductResponseDTO> products;
    private double originalPrice;
    private String coupon;
    private String errorMessage;
    private String discountPrice;

    public ShoppingCartDTO(){

    }
    private ShoppingCartDTO(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public static ShoppingCartDTO buildForError(String errorMessage){
        return new ShoppingCartDTO(errorMessage);
    }



}
