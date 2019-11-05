package com.example.phonebook.controllers;

import com.example.phonebook.entity.User;
import com.example.phonebook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public String index(Model model,@AuthenticationPrincipal User user) {
        if(user!=null) {
            model.addAttribute("username", user.getUsername());
        }
        return "index";
    }

    @GetMapping("/main")
    public String main(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("contacts", contactService.findByOwner(user));
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        return "main";
    }

}
