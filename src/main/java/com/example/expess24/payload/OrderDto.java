package com.example.expess24.payload;

import com.example.expess24.entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private Integer id;

    private Integer baskerId;

    private Integer promoCodeId;

    private Integer payTypeId;

    private Double totalCost;

    private Integer contactId;

}
