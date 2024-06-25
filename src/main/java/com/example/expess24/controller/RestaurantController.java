package com.example.expess24.controller;

import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.RestaurantDto;
import com.example.expess24.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public HttpEntity<?> addRestaurant(@RequestBody RestaurantDto restaurantDto){
        ApiResponse apiResponse = restaurantService.addRestaurant(restaurantDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneRestaurant(@PathVariable Integer id){
        RestaurantDto restaurant = restaurantService.getOneRestaurant(id);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/list")
    public HttpEntity<?> getAllRestaurants(){
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @PutMapping
    public HttpEntity<?> updateRestaurant(@RequestBody RestaurantDto restaurantDto){
        ApiResponse apiResponse = restaurantService.updateRestaurant(restaurantDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteRestaurant(@PathVariable Integer id){
        ApiResponse apiResponse = restaurantService.deleteRestaurant(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}
