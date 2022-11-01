package com.example.internship.Model;

public class Student {
    private String Code;
    private String AcademicYear;
    private String Address;
    private String Email;
    private String Gender;
    private String Major;
    private String Name;
    private String School;
    private boolean Verify;

    public Student(){}

    public Student(String code, String academicYear, String address, String email, String gender, String major, String name, String school, boolean verify) {
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

    public boolean isVerify() {
        return Verify;
    }

    public void setVerify(boolean verify) {
        Verify = verify;
    }
}
