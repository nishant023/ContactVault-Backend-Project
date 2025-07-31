package com.contactVault.ContactVault.ContactVault.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
    private Long id;
    private String name;
    private String nickname;
    private String mobile;
    private String email;

    // To show which user this contact belongs to (optional for display)
    private Long userId;
}
