package com.example.internship.Model;

import java.io.Serializable;

public class JobApp implements Serializable {
    private  String NameUser, url, NameCom, Major,  NameSchool, DateApp, Status,id_User;

    public JobApp(){}

    public JobApp(String nameUser, String url, String nameCom, String major, String nameSchool, String dateApp, String status, String user_ID) {
        NameUser = nameUser;
        this.url = url;
        NameCom = nameCom;
        Major = major;
        NameSchool = nameSchool;
        DateApp = dateApp;
        Status = status;
        id_User = user_ID;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public String getNameSchool() {
        return NameSchool;
    }

    public void setNameSchool(String nameSchool) {
        NameSchool = nameSchool;
    }

    public String getDateApp() {
        return DateApp;
    }

    public void setDateApp(String dateApp) {
        DateApp = dateApp;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameCom() {
        return NameCom;
    }

    public void setNameCom(String nameCom) {
        NameCom = nameCom;
    }
}
