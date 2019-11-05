package com.example.phonebook.service;

import com.example.phonebook.entity.Contact;
import com.example.phonebook.entity.Number;
import com.example.phonebook.repository.ContactRepository;
import com.example.phonebook.repository.NumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NumberService {

    @Autowired
    private NumberRepository numberRepository;

    @Autowired
    private ContactRepository contactRepository;

    public Set<Number> findByContact(Contact contact) {
        return numberRepository.findByContact(contact);
    }

    public void addNumber(Number number, long contactId) {
        Contact contact=contactRepository.findById(contactId).orElse(null);
        if(contact!=null)
        {
            contact.addNumber(number);
            contactRepository.saveAndFlush(contact);
        }
    }

    public void removeNumber(Number number, long contactId) {
        Contact contact=contactRepository.findById(contactId).orElse(null);
        {
            contact.removeNumber(number);
            contactRepository.saveAndFlush(contact);
        }
    }

    public Number findById(long id)
    {
        return numberRepository.findById(id).orElse(null);
    }
    public void update(Number number){
        numberRepository.saveAndFlush(number);
    }
}
