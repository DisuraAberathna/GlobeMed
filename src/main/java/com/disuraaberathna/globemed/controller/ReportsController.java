package com.disuraaberathna.globemed.controller;

import com.disuraaberathna.globemed.model.dao.BillDAO;
import com.disuraaberathna.globemed.model.dao.MedicalReportDAO;
import com.disuraaberathna.globemed.model.dao.PatientDAO;
import com.disuraaberathna.globemed.model.entity.Bill;
import com.disuraaberathna.globemed.model.entity.MedicalReport;
import com.disuraaberathna.globemed.model.entity.Patient;
import com.disuraaberathna.globemed.model.service.visitor.FinancialReportVisitor;
import com.disuraaberathna.globemed.model.service.visitor.TreatmentSummaryVisitor;
import com.disuraaberathna.globemed.util.LoggerUtil;
import com.disuraaberathna.globemed.view.ReportsView;

import javax.swing.*;
import java.util.List;
import java.util.logging.Logger;

public class ReportsController {
    private final ReportsView view;
    private final PatientDAO patientDAO;
    private final BillDAO billDAO;
    private final MedicalReportDAO medicalReportDAO;
    private List<Patient> patients;
    private final JPanel viewPanel;
    private final static Logger logger = LoggerUtil.getLogger();

    public ReportsController(ReportsView view, PatientDAO patientDAO, BillDAO billDAO, MedicalReportDAO medicalReportDAO, JPanel viewPanel) {
        this.view = view;
        this.patientDAO = patientDAO;
        this.billDAO = billDAO;
        this.medicalReportDAO = medicalReportDAO;
        this.viewPanel = viewPanel;

        loadInitialData();
        addEventListeners();
    }

    private void loadInitialData() {
        patients = patientDAO.getAllPatients();

        for (Patient patient : patients) {
            view.getPatientComboBox().addItem(patient.getFirstName() + " " + patient.getLastName());
        }
    }

    private void addEventListeners() {
        view.getGenerateBtn().addActionListener(e -> {
            Patient selectedPatient = patients.get(view.getPatientComboBox().getSelectedIndex());
            String reportType = String.valueOf(view.getTypeComboBox().getSelectedItem());

            if ("Treatment Summary".equals(reportType)) {
                generateTreatmentSummary(selectedPatient);
            } else {
                generateFinancialReport(selectedPatient);
            }
        });

        view.getClearBtn().addActionListener(e -> {
            view.clearForm();
            view.getReportArea().setText("");

        });
    }

    private void generateTreatmentSummary(Patient patient) {
        TreatmentSummaryVisitor visitor = new TreatmentSummaryVisitor();
        patient.accept(visitor);

        List<MedicalReport> medicalReports = medicalReportDAO.getMedicalReportsByPatientId(patient.getId());
        for (MedicalReport report : medicalReports) {
            report.accept(visitor);
        }

        view.getReportArea().setText(visitor.getSummary());
    }

    private void generateFinancialReport(Patient patient) {
        FinancialReportVisitor visitor = new FinancialReportVisitor();
        patient.accept(visitor);

        List<Bill> bills = billDAO.getBillsByPatient(patient.getId());
        for (Bill bill : bills) {
            bill.accept(visitor);
        }

        view.getReportArea().setText(visitor.getReport());
    }

    public void showView() {
        viewPanel.removeAll();
        viewPanel.add(view);
        SwingUtilities.updateComponentTreeUI(viewPanel);
    }
}
