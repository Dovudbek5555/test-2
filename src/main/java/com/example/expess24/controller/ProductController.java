package com.example.expess24.controller;

import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.ProductDto;
import com.example.expess24.payload.RestaurantDto;
import com.example.expess24.service.ProductService;
import com.example.expess24.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody ProductDto productDto){
        ApiResponse apiResponse = productService.addProduct(productDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getProduct(@PathVariable Integer id){
        ProductDto productDto = productService.getOneProduct(id);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/list")
    public HttpEntity<?> getAllProducts(){
        List<ProductDto> productDtos = productService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }

    @PutMapping
    public HttpEntity<?> updateProduct(@RequestBody ProductDto productDto){
        ApiResponse apiResponse = productService.updateProduct(productDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping
    public HttpEntity<?> deleteProduct(@PathVariable Integer id){
        ApiResponse apiResponse = productService.deleteProduct(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }


}
