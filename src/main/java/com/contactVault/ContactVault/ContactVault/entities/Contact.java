package com.contactVault.ContactVault.ContactVault.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Contact {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long contactId;

    public String name;

    private String nickName;

    private String mobile;

    private String email;


    // many contact can belong to one user, so no need to take any collection
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return contactId == contact.contactId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(contactId);
    }
*/

}
