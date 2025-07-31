package com.contactVault.ContactVault.ContactVault.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
public class LoginResponseDto {
    private String message;
    private String token;
}
