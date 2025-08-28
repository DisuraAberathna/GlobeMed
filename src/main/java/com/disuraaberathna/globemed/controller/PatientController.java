package com.disuraaberathna.globemed.controller;

import com.disuraaberathna.globemed.model.dao.PatientDAO;
import com.disuraaberathna.globemed.util.LoggerUtil;
import com.disuraaberathna.globemed.view.PatientView;

import javax.swing.*;
import java.util.logging.Logger;

public class PatientController {
    private final PatientView view;
    private final PatientDAO patientDAO;
    private final JPanel viewPanel;
    private final static Logger logger = LoggerUtil.getLogger();

    public PatientController(PatientView view, PatientDAO patientDAO, JPanel viewPanel) {
        this.view = view;
        this.patientDAO = patientDAO;
        this.viewPanel = viewPanel;
    }

    public void showView() {
        viewPanel.removeAll();
        viewPanel.add(view);
        SwingUtilities.updateComponentTreeUI(viewPanel);
    }
}
