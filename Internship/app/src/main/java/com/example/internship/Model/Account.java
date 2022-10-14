package com.example.internship.Model;

import java.io.Serializable;

public class Account implements Serializable {
    private String Email;
    private  String Pass;
    private String Name;
    private Integer Role;

    public Account(){}

    public Account(String email, String pass, String name, Integer role) {
        Email = email;
        Pass = pass;
        Name = name;
        Role = role;
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
