package com.example.phonebook.entity;

public enum NumberType {
    home,
    mobile,
    work;

    public static NumberType fromString(String numberType) {
        switch (numberType) {
            case "home":
                return home;
            case "mobile":
                return mobile;
            case "work":
                return work;
        }
        return null;
    }
}
