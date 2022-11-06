package com.example.internship.Model;

import java.io.Serializable;

public class Student implements Serializable {
    private String ID;
    private String Code;
    private String AcademicYear;
    private String Address;
    private String Email;
    private String Gender;
    private String Major;
    private String Name;
    private String School;
    private String Verify;

    public Student(){}

    public Student(String ID, String code, String academicYear, String address, String email, String gender, String major, String name, String school, String verify) {
        this.ID = ID;
        Code = code;
        AcademicYear = academicYear;
        Address = address;
        Email = email;
        Gender = gender;
        Major = major;
        Name = name;
        School = school;
        Verify = verify;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getAcademicYear() {
        return AcademicYear;
    }

    public void setAcademicYear(String academicYear) {
        AcademicYear = academicYear;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String getVerify() {
        return Verify;
    }

    public void setVerify(String verify) {
        Verify = verify;
    }
}
