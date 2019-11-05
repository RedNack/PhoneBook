package com.example.phonebook.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;

    private String photo;

    private String organization;

    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "contact")
    private Set<Number> numbers;

    //Все операции с контактом идут к пользователю, жадная загрузка, привязываем к юзеру
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String photo,String organization,String email, Set<Number> numbers, User owner) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.numbers = numbers;
        this.user = owner;
        this.organization=organization;
        this.email=email;
    }
    public Contact(String firstName, String lastName, String photo,String organization,String email, User owner) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.numbers = numbers;
        this.user = owner;
        this.organization=organization;
        this.email=email;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<Number> getNumbers() {
        return numbers;
    }

    public void setNumbers(Set<Number> numbers) {
        this.numbers = numbers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addNumber(Number number){
        numbers.add(number);
        number.setContact(this);
    }

    public void removeNumber(Number number){
        numbers.remove(number);
        number.setContact(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(organization, contact.organization) &&
                Objects.equals(email, contact.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, organization, email);
    }
}
