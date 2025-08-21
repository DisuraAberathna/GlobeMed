package com.disuraaberathna.globemed.model.entity;

import com.disuraaberathna.globemed.enums.ReportType;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table
public class ReportItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private ReportType type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "billing_item_id")
    private BillItem billItem;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medical_report_id")
    private MedicalReport medicalReport;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "report_id")
    private Report report;

    public ReportItem() {
    }

    public ReportItem(ReportType type, Appointment appointment, BillItem billItem, MedicalReport medicalReport, Report report) {
        this.type = type;
        this.appointment = appointment;
        this.billItem = billItem;
        this.medicalReport = medicalReport;
        this.report = report;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public BillItem getBillItem() {
        return billItem;
    }

    public void setBillItem(BillItem billItem) {
        this.billItem = billItem;
    }

    public MedicalReport getMedicalReport() {
        return medicalReport;
    }

    public void setMedicalReport(MedicalReport medicalReport) {
        this.medicalReport = medicalReport;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
