package com.example.internship.Model;

import java.io.Serializable;

public class Major implements Serializable {
    private String Name, Date;

    public Major(){}

    public Major(String name, String date) {
        Name = name;
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
