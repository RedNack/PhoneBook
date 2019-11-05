package com.example.phonebook.service;

import com.example.phonebook.entity.Contact;
import com.example.phonebook.entity.Number;
import com.example.phonebook.entity.User;
import com.example.phonebook.repository.ContactRepository;
import com.example.phonebook.repository.NumberRepository;
import com.example.phonebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private UserRepository userRepository;

    public List<Contact> findByOwner(User user) {
        return contactRepository.findByUser(user);
    }

    public void save(Contact contact, long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.addContact(contact);
            userRepository.saveAndFlush(user);
        }
    }

    public void delete(Contact contact, long userId) throws IOException {
        File uploadDir = new File(uploadPath);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if(!contact.getPhoto().equals("empty.jpg")) {
                Files.deleteIfExists(Paths.get(uploadDir.getAbsolutePath() + "/" + contact.getPhoto()));
            }
            user.removeContact(contact);
            userRepository.saveAndFlush(user);
        }
    }

    public Contact findById(long contact_id) {
        return contactRepository.findById(contact_id).orElse(null);
    }

    public void update(Contact contact) {
        contactRepository.saveAndFlush(contact);
    }

    public void editPhoto(long contact_id, MultipartFile file) throws IOException {
        Path path = Paths.get(uploadPath);
        File uploadDir = new File(uploadPath); //Директория файла
        Contact contact = contactRepository.findById(contact_id).orElse(null);
        if (contact != null) {
            if (file != null && !file.isEmpty()) { //Если файл не пустой
                if (!Files.exists(path)) {  //если нету папки  с аватарами, то создаем
                    Files.createDirectory(path);
                    System.out.println("Создали !");
                }
                String fileName = UUID.randomUUID().toString() + file.getOriginalFilename(); //Получаем имя файла исключая коллизию
                String resultDir = uploadDir.getAbsolutePath() + "/" + fileName;  //Получаем абсолютную директорию к файлу
                file.transferTo(new File(resultDir));   //Перемещаем туда файл

                if (!contact.getPhoto().equals("empty.jpg")) { //Если стояло НЕ изображение по умолчанию, то удаляем его
                    Files.deleteIfExists(Paths.get(uploadDir.getAbsolutePath() + "/" + contact.getPhoto()));
                }

                contact.setPhoto(fileName); //устанавливаем контакту аватар
                contactRepository.saveAndFlush(contact);
            }
        }
    }

    public void deletePhoto(long contact_id) throws IOException {
        File uploadDir = new File(uploadPath);
        Contact contact = contactRepository.findById(contact_id).orElse(null);
        if (contact != null) {
            if(!contact.getPhoto().equals("empty.jpg")) {
                Files.deleteIfExists(Paths.get(uploadDir.getAbsolutePath() + "/" + contact.getPhoto()));
                contact.setPhoto("empty.jpg");
                contactRepository.save(contact);
            }
        }
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

}
