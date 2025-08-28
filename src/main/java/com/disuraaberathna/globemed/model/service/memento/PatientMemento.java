package com.disuraaberathna.globemed.model.service.memento;

import com.disuraaberathna.globemed.enums.Genders;

import java.util.Date;

public class PatientMemento {
    private final String firstName;
    private final String lastName;
    private final String mobile;
    private final String email;
    private final Genders gender;
    private final Date dateOfBirth;
    private final String address;

    public PatientMemento(String firstName, String lastName, String mobile, String email, Genders gender, Date dateOfBirth, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public Genders getGender() {
        return gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }
}
