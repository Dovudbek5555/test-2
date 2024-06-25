package com.example.expess24.payload;

import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDto {

    private Integer id;

    private String name;

    private Integer contactId;

    private LocalTime openAt;

    private LocalTime closeAt;

}
