package com.disuraaberathna.globemed.model.entity;

import com.disuraaberathna.globemed.model.service.visitor.ReportElement;
import com.disuraaberathna.globemed.model.service.visitor.ReportVisitor;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "medical_report")
public class MedicalReport implements Serializable, ReportElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String diagnosis;
    private String treatment;
    private Date date;
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    public MedicalReport() {
    }

    public MedicalReport(String diagnosis, String treatment, Date date, Double price, Patient patient, User doctor) {
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.date = date;
        this.price = price;
        this.patient = patient;
        this.doctor = doctor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    @Override
    public void accept(ReportVisitor visitor) {
        visitor.visit(this);
    }
}
