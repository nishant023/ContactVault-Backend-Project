package com.contactVault.ContactVault.ContactVault.dtos;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
}
