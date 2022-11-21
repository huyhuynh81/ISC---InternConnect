package com.example.internship.Model;

import java.io.Serializable;

public class Account implements Serializable {
    private String Phone;
    private String Email;
    private String Pass;
    private String Name;
    private Integer Role;
    private String Id_account;


    public Account() {}

    public Account(String phone, String email, String pass, String name, Integer role, String id_account) {
        Phone = phone;
        Email = email;
        Pass = pass;
        Name = name;
        Role = role;
        Id_account = id_account;
    }

    public String getId_account() {
        return Id_account;
    }

    public void setId_account(String id_account) {
        Id_account = id_account;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public String getPass() {
        return Pass;
    }

    public String getName() {
        return Name;
    }

    public Integer getRole() {
        return Role;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setRole(Integer role) {
        Role = role;
    }
}
