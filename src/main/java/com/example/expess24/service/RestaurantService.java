package com.example.expess24.service;

import com.example.expess24.entity.Contact;
import com.example.expess24.entity.Restaurant;
import com.example.expess24.entity.User;
import com.example.expess24.entity.exception.GenericException;
import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.RestaurantDto;
import com.example.expess24.repository.ContactRepository;
import com.example.expess24.repository.RestaurantRepository;
import com.example.expess24.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ApiResponse addRestaurant(RestaurantDto restaurantDto) {
        if (restaurantRepository.existsByName(restaurantDto.getName())) {
            return new ApiResponse("This restaurant name is already registered", false);
        }

        Contact contact = contactRepository.findById(restaurantDto.getContactId())
                .orElseThrow(() -> GenericException.builder().message("Contact not found").statusCode(404).build());

        Restaurant restaurant = Restaurant.builder()
                .name(restaurantDto.getName())
                .contact(contact)
                .openAt(restaurantDto.getOpenAt())
                .closeAt(restaurantDto.getCloseAt())
                .build();

        restaurantRepository.save(restaurant);
        return new ApiResponse("Restaurant successfully registered", true);
    }

    public RestaurantDto getOneRestaurant(Integer id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("Restaurant not found").statusCode(400).build());

        return RestaurantDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .contactId(restaurant.getContact().getId())
                .openAt(restaurant.getOpenAt())
                .closeAt(restaurant.getCloseAt())
                .build();
    }

    public List<RestaurantDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantDto> restaurantDtos = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            RestaurantDto restaurantDto = RestaurantDto.builder()
                    .id(restaurant.getId())
                    .name(restaurant.getName())
                    .contactId(restaurant.getContact().getId())
                    .openAt(restaurant.getOpenAt())
                    .closeAt(restaurant.getCloseAt())
                    .build();
            restaurantDtos.add(restaurantDto);
        }

        return restaurantDtos;
    }

    public ApiResponse updateRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantDto.getId())
                .orElseThrow(() -> GenericException.builder().message("Restaurant not found").statusCode(400).build());

        Contact contact = contactRepository.findById(restaurantDto.getContactId())
                .orElseThrow(() -> GenericException.builder().message("Contact not found").statusCode(400).build());

        boolean b = restaurantRepository.existsByNameAndIdNot(restaurantDto.getName(), restaurant.getId());
        if (!b) {
            restaurant.setName(restaurantDto.getName());
            restaurant.setContact(contact);
            restaurant.setOpenAt(restaurantDto.getOpenAt());
            restaurant.setCloseAt(restaurantDto.getCloseAt());

            restaurantRepository.save(restaurant);
            return new ApiResponse("Restaurant successfully updated", true);
        }
        return new ApiResponse("This restaurant name is already registered", false);
    }

    public ApiResponse deleteRestaurant(Integer id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("Restaurant not found").statusCode(400).build());

        restaurantRepository.delete(restaurant);
        return new ApiResponse("Restaurant successfully deleted", true);
    }
}
