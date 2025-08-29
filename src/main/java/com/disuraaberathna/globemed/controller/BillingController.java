package com.disuraaberathna.globemed.controller;

import com.disuraaberathna.globemed.model.dao.BillDAO;
import com.disuraaberathna.globemed.model.dao.PatientDAO;
import com.disuraaberathna.globemed.model.entity.Bill;
import com.disuraaberathna.globemed.model.entity.Patient;
import com.disuraaberathna.globemed.util.LoggerUtil;
import com.disuraaberathna.globemed.view.BillingView;

import javax.swing.*;
import java.util.List;
import java.util.logging.Logger;

public class BillingController {
    private final BillingView view;
    private final PatientDAO patientDAO;
    private final BillDAO billDAO;
    private List<Patient> patientList;
    private Bill currentBill;
    private final JPanel viewPanel;
    private final static Logger logger = LoggerUtil.getLogger();

    public BillingController(BillingView view, PatientDAO patientDAO, BillDAO billDAO, JPanel viewPanel) {
        this.view = view;
        this.patientDAO = patientDAO;
        this.billDAO = billDAO;
        this.viewPanel = viewPanel;
    }

    private void loadInitialData() {
        patientList = patientDAO.getAllPatients();

        for (Patient patient : patientList) {
            view.getPatientComboBox().addItem(patient.getFirstName() + " " + patient.getLastName());
        }
    }

    public void showView() {
        viewPanel.removeAll();
        viewPanel.add(view);
        SwingUtilities.updateComponentTreeUI(viewPanel);
    }
}
