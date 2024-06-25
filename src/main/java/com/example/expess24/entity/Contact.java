package com.example.expess24.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Contact {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @ManyToOne
    private District district;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    private String additional;

}