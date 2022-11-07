package com.example.internship.Model;

import java.io.Serializable;

public class JobPost implements Serializable {
    private String Id_Post, Benefit, Gender, Location, Logo, Name, Number,Position, Required, Salary;

    public JobPost(){}

    public JobPost(String id_Post, String benefit, String gender, String location, String logo, String name, String number, String position, String required, String salary) {
        Id_Post = id_Post;
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

    public String getId_Post() {
        return Id_Post;
    }

    public void setId_Post(String id_Post) {
        Id_Post = id_Post;
    }

    public String getBenefit() {
        return Benefit;
    }

    public void setBenefit(String benefit) {
        Benefit = benefit;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
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

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getRequired() {
        return Required;
    }

    public void setRequired(String required) {
        Required = required;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }
}
