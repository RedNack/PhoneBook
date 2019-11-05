package com.example.phonebook.controllers;

import com.example.phonebook.entity.Contact;
import com.example.phonebook.entity.Number;
import com.example.phonebook.entity.User;
import com.example.phonebook.service.ContactService;
import com.example.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/addContact")
    public String addContact(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        return "addContact";
    }

    @PostMapping("/addContact")
    public String addContactPost(String firstName, String lastName, String organization,
                                 String email, @AuthenticationPrincipal User user) {
        Set<Number> numbers = new HashSet<>();
        Contact contact = new Contact(firstName, lastName, "empty.jpg", organization, email, numbers, user);
        contactService.save(contact, user.getId());
        return "redirect:/main";
    }

    @PostMapping("/editContact")
    public String editContactPost(long id, String firstName, String lastName, String organization,
                                 String email, @AuthenticationPrincipal User user) {
            Contact contact=contactService.findById(id);
            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setOrganization(organization);
            contact.setEmail(email);
            contactService.update(contact);

        return "redirect:/main";
    }
    @PostMapping("/deleteContact")
    public String deleteContact(long contact_id, @AuthenticationPrincipal User user) throws IOException {
        contactService.delete(contactService.findById(contact_id), user.getId());
        return "redirect:/main";
    }


    @PostMapping("/editPhoto")
    public String editAvatar(@RequestParam("file") MultipartFile file, long contactId, @AuthenticationPrincipal User user) throws IOException {
        contactService.editPhoto(contactId, file);
        return "redirect:/contact/" + contactId;
    }
    @PostMapping("/deletePhoto")
    public String deleteAvatar(long contactId) throws IOException {
        contactService.deletePhoto(contactId);
        return "redirect:/contact/" + contactId;
    }
}
