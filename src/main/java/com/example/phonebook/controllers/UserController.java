package com.example.phonebook.controllers;

import com.example.phonebook.entity.Contact;
import com.example.phonebook.entity.Role;
import com.example.phonebook.entity.User;
import com.example.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @GetMapping("/deleteUser")
    public String delete(@AuthenticationPrincipal User user) throws IOException {
        userService.delete(userService.findById(user.getId()));
        return "redirect:/logout";
    }

    @PostMapping("/registration")
    public String registrationPost(String username, String password) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        Set<Contact> contacts = new HashSet<>();
        User user = new User(username, password, roles, contacts);
        if (userService.findByUsername(username) == null) {
            userService.save(user);
            return "redirect:/";
        }
        else {
            return "redirect:/registration";
        }

    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
