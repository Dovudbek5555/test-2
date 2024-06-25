package com.example.expess24.payload;

import com.example.expess24.entity.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private UUID id;

    private String phoneNumber;

    private String password;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private Integer roleId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Double bonusAccount;

    private List<Integer> contactIds;

    private List<Integer> cardIds;

}
