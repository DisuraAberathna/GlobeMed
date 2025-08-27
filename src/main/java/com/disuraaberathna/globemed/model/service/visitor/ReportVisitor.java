package com.disuraaberathna.globemed.model.service.visitor;

import com.disuraaberathna.globemed.model.entity.Bill;
import com.disuraaberathna.globemed.model.entity.MedicalReport;
import com.disuraaberathna.globemed.model.entity.Patient;

public interface ReportVisitor {
    void visit(Patient patient);

    void visit(Bill bill);

    void visit(MedicalReport medicalReport);
}
