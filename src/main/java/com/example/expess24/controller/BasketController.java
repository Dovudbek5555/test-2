package com.example.expess24.controller;

import com.example.expess24.entity.PreBasket;
import com.example.expess24.entity.User;
import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.PreBasketDto;
import com.example.expess24.security.CurrentUser;
import com.example.expess24.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;


    @PutMapping("/to-cart")
    public HttpEntity<?> addToCart(@RequestBody PreBasketDto preBasketDto, @CurrentUser User currentUser){
        String phoneNumber = currentUser.getPhoneNumber();
        ApiResponse apiResponse = basketService.addProductToCart(phoneNumber, preBasketDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping("/remove/{id}")
    public HttpEntity<?> removeProductFromCart(@PathVariable Integer id, @CurrentUser User currentUser){
        String phoneNumber = currentUser.getPhoneNumber();
        ApiResponse apiResponse = basketService.removeProductFromCart(phoneNumber, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}
