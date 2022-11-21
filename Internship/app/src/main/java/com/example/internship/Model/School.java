package com.example.internship.Model;

import java.io.Serializable;

public class School implements Serializable {

    private  String NameSchool, Date;

    public School(){}

    public School(String nameSchool, String date) {
        NameSchool = nameSchool;
        Date = date;
    }

    public String getNameSchool() {
        return NameSchool;
    }

    public void setNameSchool(String nameSchool) {
        NameSchool = nameSchool;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
