package com.example.expess24.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictDto {

    private Integer id;

    private String name;

    private Integer regionId;

}
