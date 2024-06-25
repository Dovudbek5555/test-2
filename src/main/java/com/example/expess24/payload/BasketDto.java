package com.example.expess24.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketDto {

    private Integer id;

    private Integer productId;

    private Integer productCount;

}
