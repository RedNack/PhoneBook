package com.example.phonebook.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity

public class Number {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String number;

    @Enumerated(EnumType.STRING)
    private NumberType numberType;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    public Number() {
    }

    public Number(String number, NumberType numberType, Contact contact) {
        this.number = number;
        this.numberType = numberType;
        this.contact = contact;
    }

    public Number(String number, NumberType numberType) {
        this.number = number;
        this.numberType = numberType;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public NumberType getNumberType() {
        return numberType;
    }

    public void setNumberType(NumberType numberType) {
        this.numberType = numberType;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number number1 = (Number) o;
        return Objects.equals(number, number1.number) &&
                numberType == number1.numberType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, numberType);
    }
}
