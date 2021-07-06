package com.example.home;

public class UserHelperClass {
    String number;
    String name;
    String email;
    String dob;
    String gender;
    String month;

    public UserHelperClass(){

    }
    public UserHelperClass(String number, String name, String email, String dob, String gender, String month) {
        this.number = number;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.month=month;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


}
