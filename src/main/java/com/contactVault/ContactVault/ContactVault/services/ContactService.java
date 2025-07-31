package com.contactVault.ContactVault.ContactVault.services;


import com.contactVault.ContactVault.ContactVault.dtos.ContactDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {
    // Add a contact for a specific user
    ContactDto addContact(String userEmail, ContactDto contactDto);

    // Get a specific contact by its ID
    ContactDto getContactById(Long contactId,String userEmail);

    // Get all contacts of a user with pagination
    Page<ContactDto> getAllContactsOfUser(String userEmail, int page, int size, String sortBy,
                                          String sortDir);

    // Search contacts by keyword (name, email, or mobile)
    List<ContactDto> searchContacts(String userEmail, String keyword);

    // Update contact by ID
    ContactDto updateContact(Long contactId, ContactDto contactDto, String userEmail);

    // Delete contact by ID
    void deleteContact(Long contactId, String userEmail);









/*
    //add contact
    ContactDto addContact(ContactDto contactDto,String currentUserEmail);





    //create
    ContactDto createContact(String userId, ContactDto contactDto);


    //update
    ContactDto updateContact(String userId, ContactDto contactDto, String contactId);


    //delete
    void deleteContact(String userId, String contactId) throws IOException;


    //get by user and id
    ContactDto getContactById(String userId, String contactId);


    //get by user and name
    ContactDto getContactByPhone(String userId, String contactPhone);


    //get by user and email
    ContactDto getContactByEmail(String userId, String email);


    //get all contacts of user
    List<ContactDto> getAllContactOfUser(String userId);


    //search contact by keyword
    List<ContactDto> getContactByKeyword(String userId, String keyword);

*/
}
