package com.disuraaberathna.globemed.model.service.visitor;

import com.disuraaberathna.globemed.model.entity.Bill;
import com.disuraaberathna.globemed.model.entity.MedicalReport;
import com.disuraaberathna.globemed.model.entity.Patient;

import java.text.SimpleDateFormat;

public class TreatmentSummaryVisitor implements ReportVisitor{
    private final StringBuilder summary = new StringBuilder();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int reportCount = 0;

    @Override
    public void visit(Patient patient) {
        summary.append("=== TREATMENT SUMMARY ===\n");
        summary.append("Patient: ").append(patient.getFirstName()).append(" ")
                .append(patient.getLastName()).append("\n");
        summary.append("Mobile: ").append(patient.getMobile()).append("\n");
        summary.append("Email: ").append(patient.getEmail()).append("\n");
        summary.append("Gender: ").append(patient.getGender()).append("\n");
        summary.append("Date of Birth: ").append(dateFormat.format(patient.getDateOfBirth())).append("\n");
        summary.append("Address: ").append(patient.getAddress()).append("\n\n");
        summary.append("=== MEDICAL REPORTS ===\n");
    }

    @Override
    public void visit(Bill bill) {

    }

    @Override
    public void visit(MedicalReport medicalReport) {
        reportCount++;
        summary.append("\nReport #").append(reportCount).append(":\n");
        summary.append("Date: ").append(dateFormat.format(medicalReport.getDate())).append("\n");
        summary.append("Doctor: ").append(medicalReport.getDoctor().getFirstName())
                .append(" ").append(medicalReport.getDoctor().getLastName()).append("\n");
        summary.append("Diagnosis: ").append(medicalReport.getDiagnosis()).append("\n");
        summary.append("Treatment: ").append(medicalReport.getTreatment()).append("\n");
        summary.append("Price: LKR ").append(medicalReport.getPrice()).append("\n");
        summary.append("----------------------------------------");
    }

    public String getSummary() {
        if (reportCount == 0) {
            summary.append("\nNo medical reports found for this patient.\n");
        }

        summary.append("\n\nTotal Reports: ").append(reportCount);
        return summary.toString();
    }
}
