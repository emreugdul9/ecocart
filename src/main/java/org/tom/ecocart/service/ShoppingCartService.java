package org.tom.ecocart.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.tom.ecocart.Entities.DiscountType;
import org.tom.ecocart.Entities.Product;
import org.tom.ecocart.Entities.ShoppingCart;
import org.tom.ecocart.Entities.User;
import org.tom.ecocart.dto.Product.ProductRequestDTO;
import org.tom.ecocart.dto.Product.ProductResponseDTO;
import org.tom.ecocart.dto.ShoppingCart.ShoppingCartDTO;
import org.tom.ecocart.repository.IProductRepository;
import org.tom.ecocart.repository.IShoppingCartRepository;
import org.tom.ecocart.repository.IUserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final IShoppingCartRepository shoppingCartRepository;

    private final IProductRepository productRepository;

    private final IUserRepository userRepository;

    private final CouponService couponService;

    public ShoppingCartDTO addAProductIntoShoppingCart(String userId, ProductRequestDTO productRequestDTO) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        Product product = productRepository.findProductByName(productRequestDTO.getName()).orElseThrow(() -> new Exception("Product not found"));
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findShoppingCartByUserAndOpenTrue(userId);

        if (optionalShoppingCart.isPresent()) {
            optionalShoppingCart.get().getProducts().add(product);
            optionalShoppingCart.get().setTotalPrice(optionalShoppingCart.get().getTotalPrice() + product.getPrice());
            optionalShoppingCart.get().setDiscountPrice((optionalShoppingCart.get().getTotalPrice()));
            if (optionalShoppingCart.get().getCoupon() != null){
                couponService.applyACouponToShoppingCart(userId, optionalShoppingCart.get().getCoupon().getCouponId());
            }else {
                optionalShoppingCart.get().setDiscountPrice(optionalShoppingCart.get().getTotalPrice());
            }
            shoppingCartRepository.save(optionalShoppingCart.get());
            return getShoppingCartDTO(optionalShoppingCart.get());
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        List<Product> products = new ArrayList<>();
        products.add(product);
        shoppingCart.setProducts(products);
        shoppingCart.setTotalPrice(product.getPrice());
        shoppingCart.setOpen(true);
        shoppingCart.setUser(user);
        shoppingCart.setDiscountPrice(product.getPrice());
        shoppingCartRepository.save(shoppingCart);
        return getShoppingCartDTO(shoppingCart);


    }
    public static ShoppingCartDTO getShoppingCartDTO(ShoppingCart shoppingCart){
        ModelMapper modelMapper = new ModelMapper();
        List<ProductResponseDTO> productResponseDTOs = Arrays.asList(modelMapper.map(shoppingCart.getProducts(), ProductResponseDTO.class));
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setProducts(productResponseDTOs);
        shoppingCartDTO.setId(shoppingCart.getId());
        shoppingCartDTO.setOriginalPrice(shoppingCart.getTotalPrice());
        shoppingCartDTO.setDiscountPrice(String.valueOf(shoppingCart.getDiscountPrice()));

        if (shoppingCart.getCoupon() != null) shoppingCartDTO.setCoupon(shoppingCart.getCoupon().getCouponId());

        return shoppingCartDTO;
    }

    public ShoppingCartDTO removeAProductIntoShoppingCart(String userId, ProductRequestDTO productRequestDTO) throws Exception {
        Product product = productRepository.findProductByName(productRequestDTO.getName()).orElseThrow(() -> new Exception("Product not found"));
        ShoppingCart optionalShoppingCart = shoppingCartRepository.findShoppingCartByUserAndOpenTrue(userId).orElseThrow(() -> new Exception("Open Shopping cart not found"));
        if (optionalShoppingCart.getProducts().contains(product)) {
            optionalShoppingCart.getProducts().remove(product);
            optionalShoppingCart.setTotalPrice(optionalShoppingCart.getTotalPrice() - product.getPrice());
            if(optionalShoppingCart.getCoupon()!=null) {
                if (optionalShoppingCart.getCoupon().getDiscountType().equals(DiscountType.Amount)) {
                    optionalShoppingCart.setDiscountPrice(optionalShoppingCart.getTotalPrice() - optionalShoppingCart.getCoupon().getValue());

                } else {
                    double totalPrice = optionalShoppingCart.getTotalPrice();
                    double discountValue = totalPrice - (totalPrice * optionalShoppingCart.getCoupon().getValue()) / 100;
                    optionalShoppingCart.setDiscountPrice(discountValue);
                }
            }else{
                optionalShoppingCart.setDiscountPrice(optionalShoppingCart.getTotalPrice());
            }
            if(optionalShoppingCart.getProducts().size()<1){
                optionalShoppingCart.setCoupon(null);
                optionalShoppingCart.setDiscountPrice(optionalShoppingCart.getTotalPrice());
            }
            shoppingCartRepository.save(optionalShoppingCart);
            return getShoppingCartDTO(optionalShoppingCart);
        } else {
            ShoppingCartDTO shoppingCartDto = getShoppingCartDTO(optionalShoppingCart);
            shoppingCartDto.setErrorMessage("This product is not in that cart");
            return shoppingCartDto;
        }
    }

    public ShoppingCartDTO getShoppingCartByUserId(String userId) throws Exception {
        ShoppingCart optionalShoppingCart = shoppingCartRepository.findShoppingCartByUserAndOpenTrue(userId).orElseThrow(() -> new Exception("Open Shopping cart not found"));
        return getShoppingCartDTO(optionalShoppingCart);
    }



}
