package com.khalil.sms_app.models;

public class EmployeeDTO {
    private String givenName;
    private String fatherName;
    private String grandFatherName;
    private String position;
    private String email;
    private String phoneNumber;
    private Integer division;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String givenName, String fatherName, String grandFatherName, String position, String email, String phoneNumber, Integer division) {
        this.givenName = givenName;
        this.fatherName = fatherName;
        this.grandFatherName = grandFatherName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.division = division;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGrandFatherName() {
        return grandFatherName;
    }

    public void setGrandFatherName(String grandFatherName) {
        this.grandFatherName = grandFatherName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getDivision() {
        return division;
    }

    public void setDivision(Integer division) {
        this.division = division;
    }
}
