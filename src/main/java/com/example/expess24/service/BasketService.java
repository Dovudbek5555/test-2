package com.example.expess24.service;

import com.example.expess24.entity.Basket;
import com.example.expess24.entity.PreBasket;
import com.example.expess24.entity.Product;
import com.example.expess24.entity.User;
import com.example.expess24.entity.exception.GenericException;
import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.PreBasketDto;
import com.example.expess24.repository.BasketRepository;
import com.example.expess24.repository.ProductRepository;
import com.example.expess24.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final UserRepository userRepository;
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    public ApiResponse addProductToCart(String phoneNumber, PreBasketDto preBasketDto){
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> GenericException.builder().message("No user with this phone number").statusCode(404).build());
        Product product = productRepository.findById(preBasketDto.getProductId()).orElseThrow(() -> GenericException.builder().message("Product noot found /addToCart").statusCode(404).build());
        if (product.getAvailable()){
            PreBasket preBasket = PreBasket.builder()
                    .product(product)
                    .productCount(preBasketDto.getProductCount())
                    .productCost(product.getPrice()*preBasketDto.getProductCount())
                    .build();

            Basket basket = basketRepository.findByOwner(user);

            basket.getPreBasket().add(preBasket);

            basketRepository.save(basket);
            return new ApiResponse("Added to cart", true);
        }
        return new ApiResponse("Product is not available", false);
    }

    public ApiResponse removeProductFromCart(String phoneNumber,Integer id){
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> GenericException.builder().message("No user with this phone number").statusCode(404).build());
        Product product = productRepository.findById(id).orElseThrow(() -> GenericException.builder().message("Product noot found /addToCart").statusCode(404).build());
        Basket basket = basketRepository.findByOwner(user);
        boolean remove = basket.getPreBasket().remove(product);
        if (remove){
            return new ApiResponse("Product removed succesfully", true);
        }
        return new ApiResponse("Product could not be removed", false);
    }

}
