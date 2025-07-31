package com.contactVault.ContactVault.ContactVault.repositories;

import com.contactVault.ContactVault.ContactVault.entities.Contact;
import com.contactVault.ContactVault.ContactVault.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByMobile(String phone);
    Optional<Contact>findByEmail(String email);
    Page<Contact> findByUser(User user, Pageable pageable);
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId AND " +
            "(LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.mobile) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Contact> searchByUserAndKeyword(@Param("userId") Long userId, @Param("keyword") String keyword);


}
