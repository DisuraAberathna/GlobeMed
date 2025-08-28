package com.disuraaberathna.globemed.model.entity;

import com.disuraaberathna.globemed.enums.Genders;
import com.disuraaberathna.globemed.model.service.memento.PatientMemento;
import com.disuraaberathna.globemed.model.service.visitor.ReportElement;
import com.disuraaberathna.globemed.model.service.visitor.ReportVisitor;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "patients")
public class Patient implements Serializable, ReportElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String mobile;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Genders gender;
    @Column(nullable = false)
    private Date dateOfBirth;
    @Column(nullable = false)
    private String address;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String mobile, String email, Genders gender, Date dateOfBirth, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Genders getGender() {
        return gender;
    }

    public void setGender(Genders gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PatientMemento save() {
        return new PatientMemento(firstName, lastName, mobile, email, gender, dateOfBirth, address);
    }

    public void restore(PatientMemento memento) {
        this.firstName = memento.getFirstName();
        this.lastName = memento.getLastName();
        this.mobile = memento.getMobile();
        this.email = memento.getEmail();
        this.gender = memento.getGender();
        this.dateOfBirth = memento.getDateOfBirth();
        this.address = memento.getAddress();
    }

    @Override
    public void accept(ReportVisitor visitor) {
        visitor.visit(this);
    }
}
