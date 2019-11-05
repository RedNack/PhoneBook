package com.example.phonebook;

import com.example.phonebook.entity.Number;
import com.example.phonebook.entity.*;
import com.example.phonebook.repository.ContactRepository;
import com.example.phonebook.repository.NumberRepository;
import com.example.phonebook.repository.UserRepository;
import com.example.phonebook.service.ContactService;
import com.example.phonebook.service.NumberService;
import com.example.phonebook.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.TreeSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testServices {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private NumberRepository numberRepository;



    @Test
    public void addRemoveContact() {
        User user = new User("username", "password", new HashSet<Role>(), new HashSet<Contact>());
        Contact contact = new Contact("one", "last", "empty", "ooo", "email", new HashSet<Number>(), user);
        Contact contact2 = new Contact("two", "last", "empty", "ooo", "email", new HashSet<Number>(), user);
        user.addContact(contact);
        user.addContact(contact2);

        Assert.assertTrue(user.getContacts().contains(contact));
        Assert.assertTrue(user.getContacts().contains(contact2));

        user.removeContact(contact);

        Assert.assertFalse(user.getContacts().contains(contact));
        Assert.assertTrue(user.getContacts().contains(contact2));
    }

    @Test
    public void addRemoveContactPersistUser() {
        User user = new User("username", "password", new HashSet<>(), new HashSet<>());
        Contact contact = new Contact("one", "last", "empty", "ooo", "email", new HashSet<Number>(), user);
        Contact contact2 = new Contact("two", "last", "empty", "ooo", "email", new HashSet<Number>(), user);

        user.addContact(contact);
        user.addContact(contact2);

        userRepository.saveAndFlush(user);

        Assert.assertTrue(user.getContacts().contains(contact));
        Assert.assertTrue(user.getContacts().contains(contact2));

        user.removeContact(contact);

        Assert.assertFalse(user.getContacts().contains(contact));
        Assert.assertTrue(user.getContacts().contains(contact2));

        Assert.assertNotEquals(contact, contactRepository.findById(contact.getId()));

        entityManager.detach(user);

        user.addContact(contact);
        userRepository.saveAndFlush(user);

        user=userRepository.findById(user.getId()).orElse(null);
    }
}
