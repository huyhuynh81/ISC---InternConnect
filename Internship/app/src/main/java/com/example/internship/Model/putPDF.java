package com.example.internship.Model;

public class putPDF {
    String name;
    String url;
    String nameUser;
    String nameCom;
    String Major;
    String nameSchool;
    String Date;



    public putPDF(){}

    public putPDF(String name, String url, String nameUser, String nameCom, String major, String nameSchool, String date) {
        this.name = name;
        this.url = url;
        this.nameUser = nameUser;
        this.nameCom = nameCom;
        Major = major;
        this.nameSchool = nameSchool;
        Date = date;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public String getNameSchool() {
        return nameSchool;
    }

    public void setNameSchool(String nameSchool) {
        this.nameSchool = nameSchool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getNameCom() {
        return nameCom;
    }

    public void setNameCom(String nameCom) {
        this.nameCom = nameCom;
    }
}
