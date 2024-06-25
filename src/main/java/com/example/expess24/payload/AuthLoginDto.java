package com.example.expess24.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthLoginDto {

    private String phoneNumber;

    private String password;

}
