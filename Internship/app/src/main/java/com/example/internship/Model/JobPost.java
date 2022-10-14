package com.example.internship.Model;

import java.io.Serializable;

public class JobPost implements Serializable {
    private String Position, Name, Logo, Number;
    private String Location, Salary, Gender, Required, Benefit;

    public JobPost(){}

    public JobPost(String position, String name, String logo, String number, String location, String salary, String gender, String required, String benefit) {
        Position = position;
        Name = name;
        Logo = logo;
        Number = number;
        Location = location;
        Salary = salary;
        Gender = gender;
        Required = required;
        Benefit = benefit;
    }

    public String getPosition() {
        return Position;
    }

    public String getName() {
        return Name;
    }

    public String getLogo() {
        return Logo;
    }

    public String getNumber() {
        return Number;
    }

    public String getLocation() {
        return Location;
    }

    public String getSalary() {
        return Salary;
    }

    public String getGender() {
        return Gender;
    }

    public String getRequired() {
        return Required;
    }

    public String getBenefit() {
        return Benefit;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setRequired(String required) {
        Required = required;
    }

    public void setBenefit(String benefit) {
        Benefit = benefit;
    }
}
