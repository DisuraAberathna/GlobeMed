package com.disuraaberathna.globemed.model.service.visitor;

import com.disuraaberathna.globemed.model.entity.Bill;
import com.disuraaberathna.globemed.model.entity.MedicalReport;
import com.disuraaberathna.globemed.model.entity.Patient;

public class FinancialReportVisitor implements ReportVisitor {
    private final StringBuilder report = new StringBuilder();
    private double totalAmount;

    @Override
    public void visit(Patient patient) {
        report.append("Financial Report for Patient : ")
                .append(patient.getFirstName()).append(" ")
                .append(patient.getLastName()).append("\n");
    }

    @Override
    public void visit(Bill bill) {
        report.append("\n- Bill ID: ").append(bill.getId())
                .append("\n  Date: ").append(bill.getDate())
                .append("\n  Amount: LKR").append(bill.getAmount())
                .append("\n  Status: ").append(bill.getStatus());
        totalAmount += bill.getAmount();
    }

    @Override
    public void visit(MedicalReport medicalReport) {

    }

    public String getReport() {
        report.append("\n\nTotal Amount : LKR ").append(totalAmount);
        return report.toString();
    }
}
