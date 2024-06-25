package com.example.expess24.payload;

import com.example.expess24.entity.District;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDto {

    private Integer id;

    private Integer districtId;

    private String city;

    private String street;

    private String additional;

}
