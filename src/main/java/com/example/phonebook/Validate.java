package com.example.phonebook;

import java.util.List;

//firstName, lastName, organization, email, numbers
public class Validate {

    public boolean validate(List<String[]> csv) {
        boolean check = false;
        for (String[] str : csv) {
            if (str.length < 4 || str.length % 2 != 0) return false;

            if(str.length>4)
            {
                for(int i=4; i<str.length; i=i+2)
                {
                    if(!isNumeric(str[i])) return false;
                    if(!(str[i+1].equals("mobile") || str[i+1].equals("work") || str[i+1].equals("home"))) return false;
                }
            }
        }
        return true;
    }

    private boolean isNumeric(String string) {
        if (string == null) return false;
        return string.matches("^-?\\d+$");
    }
}
