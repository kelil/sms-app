package com.khalil.sms_app.models;



import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table
public class Employee implements Serializable {

    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "employee_sequence")
    private Integer id;
    private String givenName;
    private String fatherName;
    private String grandFatherName;
    private String position;
    private String email;
    private String phoneNumber;
    @ManyToOne
    private Division division;

    public Employee(){
    }
    public Employee(String givenName, String fatherName, String grandFatherName, String position, String email,
            String phoneNumber, Division division) {
        this.givenName = givenName;
        this.fatherName = fatherName;
        this.grandFatherName = grandFatherName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.division = division;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public Division getDivision() {
        return division;
    }
    public void setDivision(Division division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", givenName='" + getGivenName() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", grandFatherName='" + getGrandFatherName() + "'" +
            ", position='" + getPosition() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", division='" + getDivision().getName() + "'" +
            "}";
    }



}

