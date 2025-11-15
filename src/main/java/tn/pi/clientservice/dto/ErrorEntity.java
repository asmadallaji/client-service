package tn.pi.clientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class ErrorEntity {
    String code;
    String message;
}