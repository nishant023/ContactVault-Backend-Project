package com.contactVault.ContactVault.ContactVault.services.Impl;

import com.contactVault.ContactVault.ContactVault.dtos.ContactDto;
import com.contactVault.ContactVault.ContactVault.entities.Contact;
import com.contactVault.ContactVault.ContactVault.entities.User;
import com.contactVault.ContactVault.ContactVault.repositories.ContactRepository;
import com.contactVault.ContactVault.ContactVault.repositories.UserRepository;
import com.contactVault.ContactVault.ContactVault.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    @Override
    public ContactDto addContact(String userEmail, ContactDto contactDto) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Contact contact = modelMapper.map(contactDto, Contact.class);
        contact.setUser(user);

        Contact saved = contactRepository.save(contact);
        return modelMapper.map(saved, ContactDto.class);
    }

    @Override
    public ContactDto getContactById(Long contactId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        if (!contact.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to contact");
        }

        return modelMapper.map(contact, ContactDto.class);


    }

    @Override
    public Page<ContactDto> getAllContactsOfUser(String userEmail, int page, int size, String sortBy, String sortDir) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Contact> contactPage = contactRepository.findByUser(user, pageable);

        return contactPage.map(contact -> modelMapper.map(contact, ContactDto.class));
    }

    @Override
    public List<ContactDto> searchContacts(String userEmail, String keyword) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Contact> contacts = contactRepository.searchByUserAndKeyword(user.getId(), keyword);

        return contacts.stream()
                .map(contact -> modelMapper.map(contact, ContactDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto updateContact(Long contactId, ContactDto contactDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        if (!contact.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized update attempt");
        }

        contact.setName(contactDto.getName());
        contact.setNickName(contactDto.getNickname());
        contact.setEmail(contactDto.getEmail());
        contact.setMobile(contactDto.getMobile());

        Contact updated = contactRepository.save(contact);
        return modelMapper.map(updated, ContactDto.class);
    }

    @Override
    public void deleteContact(Long contactId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        if (!contact.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized delete attempt");
        }

        contactRepository.delete(contact);
    }











    /*
    @Override
    public ContactDto addContact(ContactDto contactDto, String currentUserEmail) {
        return null;
    }

    @Override
    public ContactDto createContact(String userId, ContactDto contactDto) {



        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException());


        return null;
    }

    @Override
    public ContactDto updateContact(String userId, ContactDto contactDto, String contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact is not found with this id"));
        User user = contact.getUser();
        contact.setContactId(contactDto.getId());
        contact.setUser(user);
        contact.setMobile(contactDto.getMobile());
        contact.setEmail(contactDto.getEmail());
        Contact saved = contactRepository.save(contact);
        return modelMapper.map(saved, ContactDto.class);
    }

    @Override
    public void deleteContact(String userId, String contactId) throws IOException {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with this id"));
        contactRepository.delete(contact);
    }

    @Override
    public ContactDto getContactById(String userId, String contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found by this id"));
        return modelMapper.map(contact, ContactDto.class);
    }

    @Override
    public ContactDto getContactByPhone(String userId, String contactPhone) {
        Contact contact = contactRepository.findByMobile(contactPhone)
                .orElseThrow(() -> new RuntimeException("Contact not found with this Phone Number"));
        return modelMapper.map(contact, ContactDto.class);
    }

    @Override
    public ContactDto getContactByEmail(String userId, String email) {
        Contact contact = contactRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Contact not found with this Phone Number"));
        return modelMapper.map(contact, ContactDto.class);
    }

    @Override
    public List<ContactDto> getAllContactOfUser(String userId) {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream().map(contact -> modelMapper.map(contact, ContactDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ContactDto> getContactByKeyword(String userId, String keyword) {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream().map(contact -> modelMapper.map(contact, ContactDto.class)).collect(Collectors.toList());
    }
*/
}
