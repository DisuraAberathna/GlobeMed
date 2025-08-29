package com.disuraaberathna.globemed.controller;

import com.disuraaberathna.globemed.model.dao.MedicalReportDAO;
import com.disuraaberathna.globemed.model.dao.PatientDAO;
import com.disuraaberathna.globemed.model.dao.UserDAO;
import com.disuraaberathna.globemed.model.entity.MedicalReport;
import com.disuraaberathna.globemed.model.entity.Patient;
import com.disuraaberathna.globemed.model.entity.User;
import com.disuraaberathna.globemed.model.service.visitor.TreatmentSummaryVisitor;
import com.disuraaberathna.globemed.util.LoggerUtil;
import com.disuraaberathna.globemed.view.MedicalReportView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class MedicalReportController {
    private final MedicalReportView view;
    private final PatientDAO patientDAO;
    private final UserDAO userDAO;
    private final MedicalReportDAO medicalReportDAO;
    private List<Patient> patientList;
    private List<User> doctorList;
    private List<MedicalReport> reportList;
    private final JPanel viewPanel;
    private final static Logger logger = LoggerUtil.getLogger();

    public MedicalReportController(MedicalReportView view, PatientDAO patientDAO, UserDAO userDAO, MedicalReportDAO medicalReportDAO, JPanel viewPanel) {
        this.view = view;
        this.patientDAO = patientDAO;
        this.userDAO = userDAO;
        this.medicalReportDAO = medicalReportDAO;
        this.viewPanel = viewPanel;

        loadInitialData();
        addEventListeners();
    }

    private void loadInitialData() {
        patientList = patientDAO.getAllPatients();
        doctorList = userDAO.getAllDoctors();
        reportList = medicalReportDAO.getAllMedicalReports();

        view.getPatientComboBox().removeAllItems();
        view.getPatientComboBox().addItem("Select Patient");
        for (Patient patient : patientList) {
            view.getPatientComboBox().addItem(patient.getFirstName() + " " + patient.getLastName());
        }

        view.getDoctorComboBox().removeAllItems();
        view.getDoctorComboBox().addItem("Select Doctor");
        for (User doctor : doctorList) {
            view.getDoctorComboBox().addItem(doctor.getFirstName() + " " + doctor.getLastName());
        }

        loadReportsTable();
    }

    private void addEventListeners() {
        view.getCreateBtn().addActionListener(e -> createMedicalReport());

        view.getClearBtn().addActionListener(e -> clearForm());

        view.getClearSearchBtn().addActionListener(e -> clearSearch());

        view.getSearchField().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchReports();
            }
        });

        view.getReportsTable().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    viewReportDetails();
                }
            }
        });
    }

    private void createMedicalReport() {
        try {
            if (view.getPatientComboBox().getSelectedIndex() <= 0) {
                view.updateStatus("Please select a patient", Color.RED);
                return;
            }

            if (view.getDoctorComboBox().getSelectedIndex() <= 0) {
                view.updateStatus("Please select a doctor", Color.RED);
                return;
            }

            String diagnosis = view.getDiagnosisField().getText().trim();
            String treatment = view.getTreatmentField().getText().trim();
            String priceText = view.getPriceField().getText().trim();

            if (diagnosis.isEmpty()) {
                view.updateStatus("Please enter diagnosis", Color.RED);
                return;
            }

            if (treatment.isEmpty()) {
                view.updateStatus("Please enter treatment", Color.RED);
                return;
            }

            if (priceText.isEmpty()) {
                view.updateStatus("Please enter price", Color.RED);
                return;
            }

            Double price = Double.parseDouble(priceText);
            Patient selectedPatient = patientList.get(view.getPatientComboBox().getSelectedIndex() - 1);
            User selectedDoctor = doctorList.get(view.getDoctorComboBox().getSelectedIndex() - 1);

            MedicalReport medicalReport = new MedicalReport();
            medicalReport.setDiagnosis(diagnosis);
            medicalReport.setTreatment(treatment);
            medicalReport.setPrice(price);
            medicalReport.setDate(new Date());
            medicalReport.setPatient(selectedPatient);
            medicalReport.setDoctor(selectedDoctor);

            medicalReportDAO.saveMedicalReport(medicalReport);

            view.updateStatus("Medical report created successfully! Report ID: " + medicalReport.getId(), Color.GREEN);
            clearForm();
            loadReportsTable();

        } catch (NumberFormatException ex) {
            view.updateStatus("Please enter a valid price", Color.RED);
        } catch (Exception ex) {
            logger.severe("Error creating medical report: " + ex.getMessage());
            view.updateStatus("Error creating medical report", Color.RED);
        }
    }

    private void viewReportDetails() {
        try {
            int selectedRow = view.getReportsTable().getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            Integer reportId = (Integer) view.getReportsTable().getValueAt(selectedRow, 0);
            MedicalReport report = medicalReportDAO.getMedicalReportById(reportId);

            if (report == null) {
                view.updateStatus("Report not found", Color.RED);
                return;
            }

            TreatmentSummaryVisitor visitor = new TreatmentSummaryVisitor();
            report.getPatient().accept(visitor);
            report.accept(visitor);

            JTextArea textArea = new JTextArea(visitor.getSummary());
            textArea.setEditable(false);
            textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(view),
                    scrollPane,
                    "Medical Report Details - ID: " + reportId,
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            logger.severe("Error viewing report details: " + ex.getMessage());
            view.updateStatus("Error viewing report details", Color.RED);
        }
    }

    private void searchReports() {
        String searchTerm = view.getSearchField().getText().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) view.getReportsTable().getModel();
        model.setRowCount(0);

        for (MedicalReport report : reportList) {
            if (report.getPatient().getFirstName().toLowerCase().contains(searchTerm) ||
                    report.getPatient().getLastName().toLowerCase().contains(searchTerm) ||
                    report.getDoctor().getFirstName().toLowerCase().contains(searchTerm) ||
                    report.getDoctor().getLastName().toLowerCase().contains(searchTerm) ||
                    report.getDiagnosis().toLowerCase().contains(searchTerm) ||
                    report.getId().toString().contains(searchTerm)) {

                model.addRow(new Object[]{
                        report.getId(),
                        report.getPatient().getFirstName() + " " + report.getPatient().getLastName(),
                        report.getDoctor().getFirstName() + " " + report.getDoctor().getLastName(),
                        report.getDiagnosis(),
                        report.getTreatment(),
                        "LKR " + report.getPrice(),
                        report.getDate()
                });
            }
        }
    }

    private void loadReportsTable() {
        reportList = medicalReportDAO.getAllMedicalReports();
        DefaultTableModel model = (DefaultTableModel) view.getReportsTable().getModel();
        model.setRowCount(0);

        for (MedicalReport report : reportList) {
            model.addRow(new Object[]{
                    report.getId(),
                    report.getPatient().getFirstName() + " " + report.getPatient().getLastName(),
                    report.getDoctor().getFirstName() + " " + report.getDoctor().getLastName(),
                    report.getDiagnosis(),
                    report.getTreatment(),
                    "LKR " + report.getPrice(),
                    report.getDate()
            });
        }
    }

    private void clearForm() {
        view.getPatientComboBox().setSelectedIndex(0);
        view.getDoctorComboBox().setSelectedIndex(0);
        view.getDiagnosisField().setText("");
        view.getTreatmentField().setText("");
        view.getPriceField().setText("");
        view.updateStatus("Ready to create report", Color.BLUE);
    }

    private void clearSearch() {
        view.getSearchField().setText("");
        loadReportsTable();
    }

    public void showView() {
        viewPanel.removeAll();
        viewPanel.add(view);
        SwingUtilities.updateComponentTreeUI(viewPanel);
    }
}
