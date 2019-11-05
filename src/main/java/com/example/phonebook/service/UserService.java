package com.example.phonebook.service;

import com.example.phonebook.entity.Contact;
import com.example.phonebook.entity.Number;
import com.example.phonebook.entity.User;
import com.example.phonebook.repository.ContactRepository;
import com.example.phonebook.repository.NumberRepository;
import com.example.phonebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class UserService implements UserDetailsService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username).orElse(null);
    }
    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    public void delete(User user) throws IOException {
        File uploadDir = new File(uploadPath);
        for (Contact ct : user.getContacts()) {
            if (!ct.getPhoto().equals("empty.jpg")) {
                Files.deleteIfExists(Paths.get(uploadDir.getAbsolutePath() + "/" + ct.getPhoto()));
            }
        }
        userRepository.delete(user);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
