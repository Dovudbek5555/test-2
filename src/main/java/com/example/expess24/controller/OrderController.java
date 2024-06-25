package com.example.expess24.controller;

import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.OrderDto;
import com.example.expess24.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public HttpEntity<?> addOrder(@RequestBody OrderDto orderDto){
        ApiResponse apiResponse = orderService.addOrder(orderDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/history/{id}")
    public HttpEntity<?> getOrderHistory(@PathVariable UUID id){
        List<OrderDto> orderHistory = orderService.getOrderHistory(id);
        return ResponseEntity.ok(orderHistory);
    }

}
