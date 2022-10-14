package com.example.internship.Model;

public class Student {
    private String AcademicYear;
    private String Address;
    private String Email;
    private String Gender;
    private String Major;
    private String Name;

    public Student(){}

    public Student(String AcademicYear, String Address, String Email, String Gender, String Major, String Name) {
        this.AcademicYear = AcademicYear;
        this.Address = Address;
        this.Email = Email;
        this.Gender = Gender;
        this.Major = Major;
        this.Name = Name;
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

    public void setAcademicYear(String AcademicYear) {
        this.AcademicYear = AcademicYear;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public void setMajor(String Major) {
        this.Major = Major;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
