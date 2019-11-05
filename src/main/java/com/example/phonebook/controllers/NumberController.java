package com.example.phonebook.controllers;

import com.example.phonebook.entity.Contact;
import com.example.phonebook.entity.Number;
import com.example.phonebook.entity.NumberType;
import com.example.phonebook.entity.User;
import com.example.phonebook.service.ContactService;
import com.example.phonebook.service.NumberService;
import com.example.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contact")
public class NumberController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private NumberService numberService;

    @Autowired
    private UserService userService;

    @GetMapping("/{contact_id}")
    public String contactPage(@AuthenticationPrincipal User user1, @PathVariable long contact_id, Model model) {
        User user=userService.findById(user1.getId());
        Contact contact = contactService.findById(contact_id);
        model.addAttribute("numbers", contact.getNumbers());
        model.addAttribute("contact", contact);
        model.addAttribute("username", user.getUsername());
        //Equals и hashset не смотрят на id (это надо надо для Hibernate)
        boolean check=false;
        for (Contact contact1: user.getContacts())
        {
            if(contact1.getId()==contact_id){
                check=true;
            }
        }
        if(check)
        {
            return "contactPage";
        }
        return "redirect:/main";
    }

    @PostMapping("/addNumber")
    public String addNumber(long contactId, String number, String numberType) {
        number=number.replaceAll(" ", "");
        NumberType numberType1 = NumberType.fromString(numberType);
        if (numberType1 != null) {
            Number number1 = new Number(number, numberType1);
            numberService.addNumber(number1, contactId);
        }
        return "redirect:/contact/" + contactId;
    }

    @PostMapping("/deleteNumber")
    public String deleteNumber(long contactId, long numberId)
    {
        numberService.removeNumber(numberService.findById(numberId), contactId);
        return "redirect:/contact/" + contactId;
    }
    @PostMapping("/editNumber")
    public String editNumber(long contactId, long numberId, String number, String numberType)
    {
        Number number1=numberService.findById(numberId);
        if(number1!=null){
            number1.setNumber(number);
            number1.setNumberType(NumberType.fromString(numberType));
        }
        numberService.update(number1);
        return "redirect:/contact/" + contactId;
    }

}
