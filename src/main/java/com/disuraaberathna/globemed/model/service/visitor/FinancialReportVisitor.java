package com.disuraaberathna.globemed.model.service.visitor;

import com.disuraaberathna.globemed.model.entity.Bill;
import com.disuraaberathna.globemed.model.entity.MedicalReport;
import com.disuraaberathna.globemed.model.entity.Patient;

import java.text.SimpleDateFormat;

public class FinancialReportVisitor implements ReportVisitor {
    private final StringBuilder report = new StringBuilder();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private double totalAmount;
    private int billCount = 0;

    @Override
    public void visit(Patient patient) {
        report.append("=== FINANCIAL REPORT ===\n");
        report.append("Patient: ").append(patient.getFirstName()).append(" ")
                .append(patient.getLastName()).append("\n");
        report.append("Mobile: ").append(patient.getMobile()).append("\n");
        report.append("Email: ").append(patient.getEmail()).append("\n");
        report.append("Gender: ").append(patient.getGender()).append("\n");
        report.append("Date of Birth: ").append(dateFormat.format(patient.getDateOfBirth())).append("\n");
        report.append("Address: ").append(patient.getAddress()).append("\n\n");
        report.append("=== BILLING HISTORY ===\n");
    }

    @Override
    public void visit(Bill bill) {
        billCount++;
        report.append("\nBill #").append(billCount).append(":\n");
        report.append("Bill ID: ").append(bill.getId()).append("\n");
        report.append("Date: ").append(dateFormat.format(bill.getDate())).append("\n");
        report.append("Doctor: ").append(bill.getAppointment().getDoctor().getFirstName())
                .append(" ").append(bill.getAppointment().getDoctor().getLastName()).append("\n");
        report.append("Amount: LKR ").append(String.format("%.2f", bill.getAmount())).append("\n");
        report.append("Status: ").append(bill.getStatus()).append("\n");

        if (bill.getInsurance() != null) {
            report.append("Insurance: ").append(bill.getInsurance().getProvider()).append("\n");
        }

        report.append("----------------------------------------");
        totalAmount += bill.getAmount();
    }

    @Override
    public void visit(MedicalReport medicalReport) {

    }

    public String getReport() {
        if (billCount == 0) {
            report.append("\nNo bills found for this patient.\n");
        }

        report.append("\n\n=== SUMMARY ===\n");
        report.append("Total Bills: ").append(billCount).append("\n");
        report.append("Total Amount: LKR ").append(String.format("%.2f", totalAmount));
        return report.toString();
    }
}
