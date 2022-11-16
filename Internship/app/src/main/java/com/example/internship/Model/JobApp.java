package com.example.internship.Model;

import java.io.Serializable;

public class JobApp implements Serializable {
    String nameUser;
    String url;
    String nameCom;
    String Major;
    String nameSchool;
    String Date;
    String Status;
    String Verify;
    String ID_User;


    public JobApp(){}

    public JobApp(String nameUser, String url, String nameCom, String major, String nameSchool, String date, String status, String verify, String ID_User) {
        this.nameUser = nameUser;
        this.url = url;
        this.nameCom = nameCom;
        Major = major;
        this.nameSchool = nameSchool;
        Date = date;
        Status = status;
        Verify = verify;
        this.ID_User = ID_User;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameCom() {
        return nameCom;
    }

    public void setNameCom(String nameCom) {
        this.nameCom = nameCom;
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getVerify() {
        return Verify;
    }

    public void setVerify(String verify) {
        Verify = verify;
    }

    public String getID_User() {
        return ID_User;
    }

    public void setID_User(String ID_User) {
        this.ID_User = ID_User;
    }
}
