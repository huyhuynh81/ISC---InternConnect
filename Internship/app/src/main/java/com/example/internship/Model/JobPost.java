package com.example.internship.Model;

import java.io.Serializable;

public class JobPost implements Serializable {
    private String  Benefit, Gender, Location, Logo, Name, Number,Position, Required, Salary;

    public JobPost(){}

    public JobPost(String benefit, String gender, String location, String logo, String name, String number, String position, String required, String salary) {
        Benefit = benefit;
        Gender = gender;
        Location = location;
        Logo = logo;
        Name = name;
        Number = number;
        Position = position;
        Required = required;
        Salary = salary;
    }

    public String getBenefit() {
        return Benefit;
    }

    public String getGender() {
        return Gender;
    }

    public String getLocation() {
        return Location;
    }

    public String getLogo() {
        return Logo;
    }

    public String getName() {
        return Name;
    }

    public String getNumber() {
        return Number;
    }

    public String getPosition() {
        return Position;
    }

    public String getRequired() {
        return Required;
    }

    public String getSalary() {
        return Salary;
    }

    public void setBenefit(String benefit) {
        Benefit = benefit;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public void setRequired(String required) {
        Required = required;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }
}
