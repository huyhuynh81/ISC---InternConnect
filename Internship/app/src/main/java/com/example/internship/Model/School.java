package com.example.internship.Model;

public class School {
    private String Code;
    private String AcademicYear;
    private String Address;
    private String Email;
    private String Gender;
    private String Major;
    private String Name;
    private String School;
    private boolean Verify;

    public School(){}

    public School(String code, String academicYear, String address, String email, String gender, String major, String name, String school, boolean verify) {
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

    public String getCode() {
        return Code;
    }

    public String getAcademicYear() {
        return AcademicYear;
    }

    public String getAddress() {
        return Address;
    }

    public String getEmail() {
        return Email;
    }

    public String getGender() {
        return Gender;
    }

    public String getMajor() {
        return Major;
    }

    public String getName() {
        return Name;
    }

    public String getSchool() {
        return School;
    }

    public boolean isVerify() {
        return Verify;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setAcademicYear(String academicYear) {
        AcademicYear = academicYear;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSchool(String school) {
        School = school;
    }

    public void setVerify(boolean verify) {
        Verify = verify;
    }
}
