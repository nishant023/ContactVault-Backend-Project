package com.contactVault.ContactVault.ContactVault.controllers;

import com.contactVault.ContactVault.ContactVault.dtos.ContactDto;
import com.contactVault.ContactVault.ContactVault.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactDto> addContact(@RequestBody ContactDto contactDto,
                                                 Principal principal) {
        System.out.println("Email from token: " + principal.getName());

        String email = principal.getName(); // comes from JWT
        ContactDto created = contactService.addContact(email, contactDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ContactDto>> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Principal principal) {

        Page<ContactDto> contacts = contactService.getAllContactsOfUser(principal.getName(), page, size, sortBy,
                sortDir);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContactDto>> searchContacts(
            @RequestParam String keyword,
            Principal principal) {

        List<ContactDto> result = contactService.searchContacts(principal.getName(), keyword);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/{contactId}")
    public ResponseEntity<ContactDto> updateContact(@PathVariable Long contactId,
                                                    @RequestBody ContactDto contactDto,
                                                    Principal principal) {
        ContactDto updated = contactService.updateContact(contactId, contactDto, principal.getName());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<?> deleteContact(@PathVariable Long contactId, Principal principal) {
        contactService.deleteContact(contactId, principal.getName());
        return ResponseEntity.ok(Map.of("message", "Contact deleted successfully"));
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable Long contactId, Principal principal) {
        ContactDto contact = contactService.getContactById(contactId, principal.getName());
        return ResponseEntity.ok(contact);
    }


/*
    @PostMapping("create/{userId}")
    public ResponseEntity<ContactDto> createContact(@Valid @PathVariable("userId") String userId,
                                                    @Valid @RequestBody ContactDto contactDto
    ) {
        ContactDto contact = contactService.createContact(userId, contactDto);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }



    @PutMapping("/updateContact/{userId}/{contactId}")
    public ResponseEntity<ContactDto> updateContact(@Valid @PathVariable("userId") String userId,
                                                    @Valid @PathVariable String contactId,
                                                    @Valid @RequestBody ContactDto contactDto
    ) {
        ContactDto updatedContact = contactService.updateContact(userId, contactDto, contactId);
        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    @DeleteMapping("/deleteContact/{userId}/contactId")
    public ResponseEntity<APIResponseMessage> deleteContact(@Valid @PathVariable("userId") String userId,
                                                            @Valid @PathVariable("contactId") String contactId
    ) throws IOException {
        contactService.deleteContact(userId, contactId);
        APIResponseMessage message = APIResponseMessage
                .builder()
                .message("contact Deleted successfully")
                .success(true)
                .httpStatus(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @GetMapping("/getContactById/{userId}/{contactId}")
    public ResponseEntity<ContactDto> getContactById(@Valid @PathVariable("userId") String userId,
                                                     @Valid @PathVariable("contactId") String contactId
    ) {
        ContactDto contact = contactService.getContactById(userId, contactId);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }


    @GetMapping("/getContactByName/{userId}/{contactPhone}")
    public ResponseEntity<ContactDto> getContactByPhone(@Valid @PathVariable("userId") String userId,
                                                       @Valid @PathVariable("contactPhone") String contactName
    ) {
        ContactDto contact = contactService.getContactByPhone(userId, contactName);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }


    @GetMapping("/getContactByEmail/{userId}/{email}")
    public ResponseEntity<ContactDto> getContactByEmail(@Valid @PathVariable("userId") String userId,
                                                        @Valid @PathVariable("email") String email
    ) {
        ContactDto contact = contactService.getContactByEmail(userId, email);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }


    @GetMapping("/getAllContacts")
    public ResponseEntity<List<ContactDto>> getAllContactOfUser(@Valid @PathVariable("userId") String userId) {
        List<ContactDto> contacts = contactService.getAllContactOfUser(userId);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }


    @GetMapping("/getContactByKeyword/{userId}/{keyword}")
    public ResponseEntity<List<ContactDto>> getContactByKeyword(@Valid @PathVariable("userId") String userId,
                                                                @Valid @PathVariable("keyword") String keyword
    ) {
        List<ContactDto> contacts = contactService.getContactByKeyword(userId, keyword);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
*/

}
