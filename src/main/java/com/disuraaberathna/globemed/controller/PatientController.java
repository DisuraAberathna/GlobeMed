package com.disuraaberathna.globemed.controller;

import com.disuraaberathna.globemed.enums.Genders;
import com.disuraaberathna.globemed.model.dao.PatientDAO;
import com.disuraaberathna.globemed.model.entity.Patient;
import com.disuraaberathna.globemed.model.service.memento.PatientCaretaker;
import com.disuraaberathna.globemed.util.LoggerUtil;
import com.disuraaberathna.globemed.view.PatientView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
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
        addEventListeners();
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

    private void updatePatientFromView() {
        if (selectedPatient == null) {
            selectedPatient = new Patient();
        }

        selectedPatient.setFirstName(view.getFnameField().getText());
        selectedPatient.setLastName(view.getLnameField().getText());
        selectedPatient.setMobile(view.getMobileField().getText());
        selectedPatient.setEmail(view.getEmailField().getText());
        selectedPatient.setGender(Genders.valueOf((String) view.getGenderComboBox().getSelectedItem()));
        selectedPatient.setDateOfBirth(view.getDobField().getDate());
        selectedPatient.setAddress(view.getAddressArea().getText());
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

    private void addEventListeners() {
        view.getSaveBtn().addActionListener(e -> {
            if (!validatePatientData()) {
                return;
            }

            updatePatientFromView();
            patientCaretaker.save(selectedPatient.save());

            try {
                patientDAO.savePatient(selectedPatient);
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Patient saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPatientTable();
                view.clearForm();
                selectedPatient = null;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Error saving patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        });

        view.getUpdateBtn().addActionListener(e -> {
            if (selectedPatient == null || selectedPatient.getId() == null) {
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Please select a patient to update.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validatePatientData()) {
                return;
            }

            updatePatientFromView();
            patientCaretaker.save(selectedPatient.save());

            try {
                patientDAO.updatePatient(selectedPatient);
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Patient updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPatientTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Error updating patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        });

        view.getUndoBtn().addActionListener(e -> {
            if (selectedPatient != null) {
                selectedPatient.restore(patientCaretaker.undo());
                updateView(selectedPatient);
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Changes undone.", "Undo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(viewPanel.getParent(), "No patient selected to undo changes.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getCancelBtn().addActionListener(e -> {
            view.clearForm();
            selectedPatient = null;
            view.getPatientsTable().clearSelection();
        });

        view.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() != KeyEvent.VK_ENTER) {
                    return;
                }

                String searchTerm = view.getSearchField().getText().trim().toLowerCase();

                if (searchTerm.isEmpty()) {
                    loadPatientTable();
                    return;
                }

                List<Patient> filteredPatients = allPatients.stream()
                        .filter(p -> p.getFirstName().toLowerCase().contains(searchTerm) ||
                                p.getLastName().toLowerCase().contains(searchTerm) ||
                                p.getMobile().toLowerCase().contains(searchTerm) ||
                                (p.getEmail() != null && p.getEmail().toLowerCase().contains(searchTerm)))
                        .toList();

                updatePatientsTable(filteredPatients);
            }
        });

        view.getPatientsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getPatientsTable().getSelectedRow();

                if (row >= 0) {
                    selectPatientFromTable(row);
                }
            }
        });

        view.getClearBtn().addActionListener(e -> {
            view.getSearchField().setText("");
            loadPatientTable();
        });
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
