package com.contactVault.ContactVault.ContactVault.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name field is required !!")
    @Size(min = 3, max = 20, message = "min 3 and max 20 characters are allowed!!")
    private String name;


//    @Column(unique = true)
//    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email !!")
//    @Column(unique = true)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull
    @Size(min = 4, message = "min 4 characters are allowed !!")
    private String password;
    private String mobile;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();
    /*
     One user can have many contacts
    If orphanRemoval = true is set on a one-to-many or one-to-one relationship,
    and the child is removed from the parent,
    then the child becomes an orphan — and it will be automatically deleted from the database when the
     parent is persisted
    */
}
