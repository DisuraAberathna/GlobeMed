package com.disuraaberathna.globemed.controller;

import com.disuraaberathna.globemed.model.dao.PatientDAO;
import com.disuraaberathna.globemed.model.entity.Patient;
import com.disuraaberathna.globemed.model.service.memento.PatientCaretaker;
import com.disuraaberathna.globemed.util.LoggerUtil;
import com.disuraaberathna.globemed.view.PatientView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

public class PatientController {
    private final PatientView view;
    private final PatientDAO patientDAO;
    private final PatientCaretaker patientCaretaker;
    private Patient selectedPatient;
    private final JPanel viewPanel;
    private List<Patient> allPatients;
    private final static Logger logger = LoggerUtil.getLogger();

    public PatientController(PatientView view, PatientDAO patientDAO, JPanel viewPanel) {
        this.view = view;
        this.patientDAO = patientDAO;
        this.viewPanel = viewPanel;
        this.patientCaretaker = new PatientCaretaker();

        loadPatientTable();
    }

    private void loadPatientTable() {
        allPatients = patientDAO.getAllPatients();
        updatePatientsTable(allPatients);
    }

    private void updatePatientsTable(List<Patient> patients) {
        DefaultTableModel model = (DefaultTableModel) view.getPatientsTable().getModel();
        model.setRowCount(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Patient patient : patients) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(patient.getId()));
            row.add(patient.getFirstName());
            row.add(patient.getLastName());
            row.add(patient.getMobile());
            row.add(patient.getEmail() != null ? patient.getEmail() : "");
            row.add(patient.getGender().toString());
            row.add(dateFormat.format(patient.getDateOfBirth()));
            row.add(patient.getAddress());
            model.addRow(row);
        }
    }

    private void selectPatientFromTable(int row) {
        if (row >= 0 && row < allPatients.size()) {
            selectedPatient = allPatients.get(row);
            updateView(selectedPatient);
            patientCaretaker.save(selectedPatient.save());
        }
    }

    private void updateView(Patient patient) {
        if (patient == null) {
            return;
        }
        view.getIdField().setText(String.valueOf(patient.getId()));
        view.getFnameField().setText(patient.getFirstName());
        view.getLnameField().setText(patient.getLastName());
        view.getMobileField().setText(patient.getMobile());
        view.getEmailField().setText(patient.getEmail() != null ? patient.getEmail() : "");
        view.getGenderComboBox().setSelectedItem(String.valueOf(patient.getGender()));
        view.getDobField().setDate(patient.getDateOfBirth());
        view.getAddressArea().setText(patient.getAddress());
    }


    private boolean validatePatientData() {
        if (view.getFnameField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "First name is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (view.getLnameField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "Last name is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (view.getMobileField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "Mobile number is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (view.getAddressArea().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "Address is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public void setPatient(Patient patient) {
        this.selectedPatient = patient;
        updateView(patient);
        patientCaretaker.save(patient.save());
    }

    public void showView() {
        viewPanel.removeAll();
        viewPanel.add(view);
        SwingUtilities.updateComponentTreeUI(viewPanel);
    }
}
