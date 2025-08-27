package com.disuraaberathna.globemed.model.service.visitor;

import com.disuraaberathna.globemed.model.entity.Bill;
import com.disuraaberathna.globemed.model.entity.MedicalReport;
import com.disuraaberathna.globemed.model.entity.Patient;

public class TreatmentSummaryVisitor implements ReportVisitor{
    private final StringBuilder summary = new StringBuilder();

    @Override
    public void visit(Patient patient) {
        summary.append("Treatment Summary for Patient: ")
                .append(patient.getFirstName()).append(" ")
                .append(patient.getLastName()).append("\n");
    }

    @Override
    public void visit(Bill bill) {

    }

    @Override
    public void visit(MedicalReport medicalReport) {
        summary.append("\n- Date: ").append(medicalReport.getDate())
                .append("\n  Diagnosis: ").append(medicalReport.getDiagnosis())
                .append("\n  Treatment: ").append(medicalReport.getTreatment())
                .append("\n  Doctor: ").append(medicalReport.getDoctor().getFirstName())
                .append(" ").append(medicalReport.getDoctor().getLastName());
    }

    public String getSummary() {
        return summary.toString();
    }
}
