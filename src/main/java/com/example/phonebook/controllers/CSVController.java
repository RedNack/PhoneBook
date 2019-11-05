package com.example.phonebook.controllers;

import com.example.phonebook.Validate;
import com.example.phonebook.entity.Contact;
import com.example.phonebook.entity.Number;
import com.example.phonebook.entity.NumberType;
import com.example.phonebook.entity.User;
import com.example.phonebook.service.ContactService;
import com.example.phonebook.service.UserService;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class CSVController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @PostMapping("/export")
    public void exportContact(long contactId, HttpServletResponse response) throws IOException {

        String csv = "storage/data.csv";
        Contact contact = contactService.findById(contactId);
        CSVWriter writer = new CSVWriter(new FileWriter(csv, true));

        if (contact != null) {
            String[] line = getLine(contact);
            writer.writeNext(line);
        }
        writer.close();

        response.setHeader("Content-disposition", "attachment;filename=contact.csv");
        response.setContentType("application/vnd.ms-excel");
        Files.copy(Paths.get(csv), response.getOutputStream());
        response.getOutputStream().flush();

        Files.deleteIfExists(Paths.get(csv));
    }

    @GetMapping("/exportAll")
    public void exportAll(@AuthenticationPrincipal User user, HttpServletResponse response) throws IOException {

        String csv = "storage/data.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
        List<Contact> contacts = contactService.findByOwner(user);
        for (Contact contact : contacts) {
            String[] line = getLine(contact);
            writer.writeNext(line);
        }
        writer.close();

        response.setHeader("Content-disposition", "attachment;filename=all_contacts.csv");
        response.setContentType("application/vnd.ms-excel");
        Files.copy(Paths.get(csv), response.getOutputStream());
        response.getOutputStream().flush();

        Files.deleteIfExists(Paths.get(csv));
    }

    @PostMapping("/import")
    public String importContacts(MultipartFile file, @AuthenticationPrincipal User user1) throws IOException, CsvException {
        User user=userService.findById(user1.getId());
        Path path = Paths.get(uploadPath);
        File uploadDir = new File(uploadPath); //Директория файла
        if (file != null && !file.isEmpty()) { //Если файл не пустой
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            String fileName = UUID.randomUUID().toString() + file.getOriginalFilename(); //Получаем имя файла исключая коллизию
            String resultDir = uploadDir.getAbsolutePath() + "/" + fileName;  //Получаем абсолютную директорию к файлу
            file.transferTo(new File(resultDir));   //Перемещаем туда файл

            CSVReader reader = new CSVReader(new FileReader(resultDir));
            List<String[]> rows = reader.readAll();
            reader.close();
            Validate validate = new Validate();
            if (validate.validate(rows)) {
                Set<Number> numbers;
                for (String[] row : rows) {
                    Contact contact = new Contact(row[0], row[1], "empty.jpg", row[2], row[3], new HashSet<>(), user);

                    if (user.getContacts().contains(contact)) //Что бы не удалять номера  у уже существующего контакта, а только добавлять новые
                    {
                        for (Contact contact1 : user.getContacts()) {
                            if (contact.equals(contact1)) {
                                contact = contact1;
                                break;
                            }
                        }
                    }else
                    {
                        contact.setNumbers(new HashSet<>());
                    }
                    numbers = new HashSet<>();
                    for (int i = 4; i < row.length; i = i + 2) {
                        Number number = new Number(row[i], NumberType.fromString(row[i + 1]));
                        number.setContact(contact);
                        numbers.add(number);
                    }
                    contact.getNumbers().addAll(numbers);
                    contactService.save(contact, user.getId());
                    numbers.clear();
                }
            }
            Files.deleteIfExists(Paths.get(resultDir));
        }

        return "redirect:/main";
    }

    private String[] getLine(Contact contact) {
        ArrayList<String> toCSV = new ArrayList<>();
        if (contact != null) {
            toCSV.add(contact.getFirstName());
            toCSV.add(contact.getLastName());
            toCSV.add(contact.getOrganization());
            toCSV.add(contact.getEmail());
            for (Number number : contact.getNumbers()) {
                toCSV.add(number.getNumber());
                toCSV.add(number.getNumberType().toString());
            }
            String[] line = new String[toCSV.size()];
            toCSV.toArray(line);
            return line;
        }
        return null;
    }
}
