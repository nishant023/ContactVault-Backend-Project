package com.contactVault.ContactVault.ContactVault.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponseMessage {
    private String message;
    private Boolean success;
    private HttpStatus httpStatus;

}
