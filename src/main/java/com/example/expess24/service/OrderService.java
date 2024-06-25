package com.example.expess24.service;

import com.example.expess24.entity.*;
import com.example.expess24.entity.exception.GenericException;
import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.OrderDto;
import com.example.expess24.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final ContactRepository contactRepository;
    private final PayTypeRepository payTypeRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final UserRepository userRepository;

    public ApiResponse addOrder(OrderDto orderDto) {
        Double totalCost = 0.0;
        Basket basket = basketRepository.findById(orderDto.getBaskerId()).orElseThrow(() -> GenericException.builder().message("basket not found /addOrder").statusCode(404).build());
        Contact contact = contactRepository.findById(orderDto.getContactId()).orElseThrow(() -> GenericException.builder().message("contact not found /addOrder").statusCode(404).build());
        PayType payType = payTypeRepository.findById(orderDto.getPayTypeId()).orElseThrow(() -> GenericException.builder().message("paytype not found /addOrder").statusCode(404).build());
        if (orderDto.getPromoCodeId() != null) {
            PromoCode promoCode = promoCodeRepository.findById(orderDto.getPromoCodeId()).orElseThrow(() -> GenericException.builder().message("paytype not found /addOrder").statusCode(404).build());
            for (PreBasket preBasket : basket.getPreBasket()) {
                totalCost = totalCost + preBasket.getProductCost();
            }
            Order order = Order.builder()
                    .preBasket(basket.getPreBasket())
                    .promoCode(promoCode)
                    .payType(payType)
                    .totalCost(totalCost - promoCode.getValue())
                    .user(basket.getOwner())
                    .contact(contact)
                    .build();

            basket.setPreBasket(new ArrayList<PreBasket>());
            basketRepository.save(basket);
            orderRepository.save(order);

            return new ApiResponse("Succesfully order reserved", true);

        } else {

            for (PreBasket preBasket : basket.getPreBasket()) {
                totalCost =totalCost +preBasket.getProductCost();
            }
            Order order = Order.builder()
                    .preBasket(basket.getPreBasket())
                    .payType(payType)
                    .totalCost(totalCost)
                    .user(basket.getOwner())
                    .contact(contact)
                    .build();

            basket.setPreBasket(new ArrayList<PreBasket>());
            basketRepository.save(basket);
            orderRepository.save(order);

            return new ApiResponse("Succesfully order reserved", true);
        }
    }

    public List<OrderDto>  getOrderHistory(UUID id){
        User user = userRepository.findById(id).orElseThrow(() -> GenericException.builder().message("Cant find user /getOrderHistory").statusCode(404).build());
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = OrderDto.builder()
                    .id(order.getId())
                    .baskerId(basketRepository.findByOwner(user).getId())
                    .totalCost(order.getTotalCost())
                    .contactId(order.getContact().getId())
                    .promoCodeId(order.getPromoCode().getId())
                    .payTypeId(order.getPayType().getId())
                    .build();
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

}
