package com.example.expess24.service;

import com.example.expess24.entity.*;
import com.example.expess24.entity.exception.GenericException;
import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.ProductDto;
import com.example.expess24.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MeasureRepository measureRepository;
    private final RestaurantRepository restaurantRepository;

    public ApiResponse addProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> GenericException.builder().message("Category not found").statusCode(404).build());
        Measure measure = measureRepository.findById(productDto.getMeasureId())
                .orElseThrow(() -> GenericException.builder().message("Measure not found").statusCode(404).build());
        Restaurant restaurant = restaurantRepository.findById(productDto.getRestaurantId())
                .orElseThrow(() -> GenericException.builder().message("Restaurant not found").statusCode(404).build());

        if (productDto.getAvailable() != null) {
            if (productDto.getPrice() != null) {
                Product product = Product.builder()
                        .name(productDto.getName())
                        .description(productDto.getDescription())
                        .category(category)
                        .price(productDto.getPrice())
                        .measure(measure)
                        .restaurant(restaurant)
                        .available(productDto.getAvailable())
                        .build();

                productRepository.save(product);
                return new ApiResponse("Product successfully added", true);
            }
            return new ApiResponse("Please provide the price", false);
        }
        return new ApiResponse("Please provide availability", false);
    }

    public ProductDto getOneProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("Product not found").statusCode(400).build());

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .price(product.getPrice())
                .measureId(product.getMeasure().getId())
                .restaurantId(product.getRestaurant().getId())
                .available(product.getAvailable())
                .build();
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            ProductDto productDto = ProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .categoryId(product.getCategory().getId())
                    .price(product.getPrice())
                    .measureId(product.getMeasure().getId())
                    .restaurantId(product.getRestaurant().getId())
                    .available(product.getAvailable())
                    .build();
            productDtos.add(productDto);
        }

        return productDtos;
    }

    public ApiResponse updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(() -> GenericException.builder().message("Product not found").statusCode(400).build());

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> GenericException.builder().message("Category not found").statusCode(400).build());
        Measure measure = measureRepository.findById(productDto.getMeasureId())
                .orElseThrow(() -> GenericException.builder().message("Measure not found").statusCode(400).build());
        Restaurant restaurant = restaurantRepository.findById(productDto.getRestaurantId())
                .orElseThrow(() -> GenericException.builder().message("Restaurant not found").statusCode(400).build());

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        product.setMeasure(measure);
        product.setRestaurant(restaurant);
        product.setAvailable(productDto.getAvailable());
        productRepository.save(product);

        return new ApiResponse("Product succesfully updated", true);
    }

    public ApiResponse deleteProduct(Integer id){
        Product product = productRepository.findById(id).orElseThrow(() -> GenericException.builder().message("Product not found").statusCode(404).build());

        productRepository.delete(product);
        return new ApiResponse("Product succesfully deleted", true);

    }

}
