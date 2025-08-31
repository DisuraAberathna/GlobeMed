package com.disuraaberathna.globemed.controller;

import com.disuraaberathna.globemed.model.dao.*;
import com.disuraaberathna.globemed.model.entity.User;
import com.disuraaberathna.globemed.model.service.mediator.AppointmentScheduler;
import com.disuraaberathna.globemed.view.*;

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
            case DOCTOR:
                dashboard.getPatientRecordsBtn().setEnabled(true);
                dashboard.getAppointmentScheduleBtn().setEnabled(true);
                dashboard.getBillingBtn().setEnabled(true);
                dashboard.getReportsBtn().setEnabled(true);
                dashboard.getManageStaffBtn().setEnabled(true);
                break;
            case NURSE:
                dashboard.getPatientRecordsBtn().setEnabled(true);
                dashboard.getAppointmentScheduleBtn().setEnabled(true);
                dashboard.getBillingBtn().setEnabled(false);
                dashboard.getReportsBtn().setEnabled(true);
                dashboard.getManageStaffBtn().setEnabled(false);
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

        dashboard.getAppointmentScheduleBtn().addActionListener(e -> {
            AppointmentDAO appointmentDAO = new AppointmentDAO();

            new AppointmentController(
                    new AppointmentView(),
                    new AppointmentScheduler(appointmentDAO, dashboard),
                    new UserDAO(), new PatientDAO(), appointmentDAO, dashboard.getViewPanel()
            ).showView();
        });

        dashboard.getBillingBtn().addActionListener(e -> {
            new BillingController(
                    new BillingView(), new PatientDAO(),
                    new BillDAO(), new AppointmentDAO(), dashboard.getViewPanel()
            ).showView();
        });

        dashboard.getReportsBtn().addActionListener(e -> {
            new ReportsController(
                    new ReportsView(), new PatientDAO(),
                    new BillDAO(), new MedicalReportDAO(), new UserDAO(), dashboard.getViewPanel()
            ).showView();
        });

        dashboard.getManageStaffBtn().addActionListener(e -> {
            new StaffManagementController(new StaffManagementView(), new UserDAO(), dashboard.getViewPanel()).showView();
        });
    }
}
