package com.example.internship.Model;

import java.io.Serializable;

public class AccountCompany implements Serializable {
    private String Phone;
    private String Email;
    private String Pass;
    private String Name;
    private String Company;
    private Integer Role;


    public AccountCompany() {}

    public AccountCompany(String phone, String email, String pass, String name, String company, Integer role) {
        Phone = phone;
        Email = email;
        Pass = pass;
        Name = name;
        Company = company;
        Role = role;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public Integer getRole() {
        return Role;
    }

    public void setRole(Integer role) {
        Role = role;
    }
}
