package com.example.expess24.entity.exception;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericException extends RuntimeException{
    private String message;
    private Integer statusCode;
}
