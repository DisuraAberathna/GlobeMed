package com.disuraaberathna.globemed.controller;

import com.disuraaberathna.globemed.model.dao.PatientDAO;
import com.disuraaberathna.globemed.model.dao.UserDAO;
import com.disuraaberathna.globemed.model.entity.User;
import com.disuraaberathna.globemed.view.Dashboard;
import com.disuraaberathna.globemed.view.PatientView;
import com.disuraaberathna.globemed.view.StaffManagementView;

public class DashboardController {
    private final Dashboard dashboard;
    private final User user;

    public DashboardController(Dashboard dashboard, User user) {
        this.dashboard = dashboard;
        this.user = user;

        setPermissions();
        addEventListeners();

        new PatientController(new PatientView(), new PatientDAO(), dashboard.getViewPanel()).showView();
    }

    private void setPermissions() {
        switch (user.getRole()) {
            case DOCTOR, NURSE:
                dashboard.getPatientRecordsBtn().setEnabled(true);
                dashboard.getAppointmentScheduleBtn().setEnabled(true);
                dashboard.getBillingBtn().setEnabled(true);
                dashboard.getReportsBtn().setEnabled(true);
                dashboard.getManageStaffBtn().setEnabled(true);
                break;
            case PHARMACIST:
                dashboard.getPatientRecordsBtn().setEnabled(true);
                dashboard.getAppointmentScheduleBtn().setEnabled(false);
                dashboard.getBillingBtn().setEnabled(true);
                dashboard.getReportsBtn().setEnabled(false);
                dashboard.getManageStaffBtn().setEnabled(false);
                break;
            case RECEPTION:
                dashboard.getPatientRecordsBtn().setEnabled(false);
                dashboard.getAppointmentScheduleBtn().setEnabled(true);
                dashboard.getBillingBtn().setEnabled(true);
                dashboard.getReportsBtn().setEnabled(false);
                dashboard.getManageStaffBtn().setEnabled(false);
                break;
            default:
                dashboard.getPatientRecordsBtn().setEnabled(false);
                dashboard.getAppointmentScheduleBtn().setEnabled(false);
                dashboard.getBillingBtn().setEnabled(false);
                dashboard.getReportsBtn().setEnabled(false);
                dashboard.getManageStaffBtn().setEnabled(false);
                break;
        }
    }

    public void showDashboard() {
        dashboard.setUserInfo(user.getFirstName() + " " + user.getLastName(), user.getRole().name());
        dashboard.setVisible(true);
    }

    private void addEventListeners() {
        dashboard.getPatientRecordsBtn().addActionListener(e -> {
            new PatientController(new PatientView(), new PatientDAO(), dashboard.getViewPanel()).showView();
        });

        dashboard.getManageStaffBtn().addActionListener(e -> {
            new StaffManagementController(new StaffManagementView(), new UserDAO(), dashboard.getViewPanel()).showView();
        });
    }
}
