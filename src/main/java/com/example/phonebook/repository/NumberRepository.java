package com.example.phonebook.repository;

import com.example.phonebook.entity.Contact;
import com.example.phonebook.entity.Number;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NumberRepository extends JpaRepository<Number, Long> {
    Set<Number> findByContact(Contact contact);
}
