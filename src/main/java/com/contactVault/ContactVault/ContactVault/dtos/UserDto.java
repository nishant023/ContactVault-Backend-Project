package com.contactVault.ContactVault.ContactVault.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String mobile;

    // List of contact DTOs associated with this user
    private List<ContactDto> contacts;

}
