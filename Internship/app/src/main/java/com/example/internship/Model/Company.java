package com.example.internship.Model;

import java.io.Serializable;

public class Company implements Serializable {

    private  String Email, Location, Logo, Name,Phone;

    public Company(){};

    public Company(String email, String location, String logo, String name, String phone) {
        Email = email;
        Location = location;
        Logo = logo;
        Name = name;
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
