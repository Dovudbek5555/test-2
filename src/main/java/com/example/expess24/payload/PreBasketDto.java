package com.example.expess24.payload;

import com.example.expess24.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreBasketDto {

    private Integer id;

    private Integer productId;

    private Integer productCount;

    private Double productCost;

}
