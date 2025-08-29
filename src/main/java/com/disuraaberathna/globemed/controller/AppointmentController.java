package com.disuraaberathna.globemed.controller;

import com.disuraaberathna.globemed.enums.Status;
import com.disuraaberathna.globemed.enums.UserRoles;
import com.disuraaberathna.globemed.model.dao.AppointmentDAO;
import com.disuraaberathna.globemed.model.dao.PatientDAO;
import com.disuraaberathna.globemed.model.dao.UserDAO;
import com.disuraaberathna.globemed.model.entity.Appointment;
import com.disuraaberathna.globemed.model.entity.Patient;
import com.disuraaberathna.globemed.model.entity.User;
import com.disuraaberathna.globemed.model.service.mediator.AppointmentScheduler;
import com.disuraaberathna.globemed.util.LoggerUtil;
import com.disuraaberathna.globemed.view.AppointmentView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentController {
    private final AppointmentView view;
    private final AppointmentScheduler appointmentScheduler;
    private final UserDAO userDAO;
    private final PatientDAO patientDAO;
    private final AppointmentDAO appointmentDAO;
    private java.util.List<User> doctors;
    private java.util.List<Patient> patients;
    private List<Appointment> allAppointments;
    private final JPanel viewPanel;
    private final static Logger logger = LoggerUtil.getLogger();

    public AppointmentController(AppointmentView view, AppointmentScheduler appointmentScheduler, UserDAO userDAO, PatientDAO patientDAO, AppointmentDAO appointmentDAO, JPanel viewPanel) {
        this.view = view;
        this.appointmentScheduler = appointmentScheduler;
        this.userDAO = userDAO;
        this.patientDAO = patientDAO;
        this.appointmentDAO = appointmentDAO;
        this.viewPanel = viewPanel;

        addEventListeners();
        loadInitialData();
        loadAppointmentsTable();
    }

    private void addEventListeners() {
        view.getScheduleBtn().addActionListener(e -> {
            if (!validateAppointmentData()) {
                return;
            }

            try {
                User doctor = doctors.get(view.getDoctorComboBox().getSelectedIndex());
                Patient patient = patients.get(view.getPatientComboBox().getSelectedIndex());
                Date date = view.getDateField().getDate();
                String time = view.getTimeField().getValue().toString();

                Appointment appointment = new Appointment(doctor, patient, date, time, Status.SCHEDULED);

                if (appointmentScheduler.isConflict(appointment)) {
                    view.updateStatus("Conflict detected! Doctor is busy at this time.", new Color(231, 76, 60));
                    JOptionPane.showMessageDialog(viewPanel.getParent(), "Conflict detected! Doctor is busy at this time.", "Scheduling Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                appointmentScheduler.schedule(appointment);
                view.updateStatus("Appointment scheduled successfully!", new Color(46, 204, 113));
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Appointment scheduled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                loadAppointmentsTable();
                view.clearForm();
            } catch (Exception ex) {
                view.updateStatus("Error scheduling appointment: " + ex.getMessage(), new Color(231, 76, 60));
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Error scheduling appointment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        });

        view.getRescheduleBtn().addActionListener(e -> {
            int selectedRow = view.getAppointmentsTable().getSelectedRow();

            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Please select an appointment to reschedule.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validateAppointmentData()) {
                return;
            }

            try {
                Appointment appointment = allAppointments.get(selectedRow);

                User doctor = doctors.get(view.getDoctorComboBox().getSelectedIndex());
                Patient patient = patients.get(view.getPatientComboBox().getSelectedIndex());
                Date date = view.getDateField().getDate();
                String time = view.getTimeField().getValue().toString();

                appointment.setDoctor(doctor);
                appointment.setPatient(patient);
                appointment.setAppointedDate(date);
                appointment.setAppointedTime(time);

                appointmentScheduler.reschedule(appointment);
                view.updateStatus("Appointment rescheduled successfully!", new Color(46, 204, 113));
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Appointment rescheduled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                loadAppointmentsTable();
                view.clearForm();
            } catch (Exception ex) {
                view.updateStatus("Error rescheduling appointment: " + ex.getMessage(), new Color(231, 76, 60));
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Error rescheduling appointment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        });

        view.getCancelBtn().addActionListener(e -> {
            int selectedRow = view.getAppointmentsTable().getSelectedRow();

            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(viewPanel.getParent(), "Please select an appointment to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(viewPanel.getParent(),
                    "Are you sure you want to cancel this appointment?",
                    "Confirm Cancellation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Appointment appointment = allAppointments.get(selectedRow);
                    appointmentScheduler.cancel(appointment);
                    view.updateStatus("Appointment canceled successfully!", new Color(46, 204, 113));
                    JOptionPane.showMessageDialog(viewPanel.getParent(), "Appointment canceled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    loadAppointmentsTable();
                    view.clearForm();
                } catch (Exception ex) {
                    view.updateStatus("Error canceling appointment: " + ex.getMessage(), new Color(231, 76, 60));
                    JOptionPane.showMessageDialog(viewPanel.getParent(), "Error canceling appointment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        });

        view.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() != KeyEvent.VK_ENTER) {
                    return;
                }

                String searchTerm = view.getSearchField().getText().trim().toLowerCase();

                if (searchTerm.isEmpty()) {
                    loadAppointmentsTable();
                    view.updateStatus("Showing all appointments", new Color(52, 152, 219));
                    return;
                }

                List<Appointment> filteredAppointments = allAppointments.stream()
                        .filter(app -> app.getDoctor().getFirstName().toLowerCase().contains(searchTerm) ||
                                app.getDoctor().getLastName().toLowerCase().contains(searchTerm) ||
                                app.getPatient().getFirstName().toLowerCase().contains(searchTerm) ||
                                app.getPatient().getLastName().toLowerCase().contains(searchTerm) ||
                                app.getStatus().toString().toLowerCase().contains(searchTerm))
                        .toList();

                updateAppointmentsTable(filteredAppointments);
                view.updateStatus("Found " + filteredAppointments.size() + " appointment(s)", new Color(52, 152, 219));

            }
        });

        view.getAppointmentsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getAppointmentsTable().getSelectedRow();

                if (row >= 0) {
                    selectAppointmentFromTable(row);
                }
            }
        });

        view.getClearBtn().addActionListener(e -> {
            view.getSearchField().setText("");
            loadAppointmentsTable();
        });
    }

    private void loadInitialData() {
        doctors = userDAO.getUsersByRoles(UserRoles.DOCTOR);
        patients = patientDAO.getAllPatients();

        for (User doctor : doctors) {
            view.getDoctorComboBox().addItem(doctor.getFirstName() + " " + doctor.getLastName());
        }

        for (Patient patient : patients) {
            view.getPatientComboBox().addItem(patient.getFirstName() + " " + patient.getLastName());
        }
    }

    private void loadAppointmentsTable() {
        allAppointments = appointmentDAO.getAllAppointments();
        updateAppointmentsTable(allAppointments);
    }

    private void updateAppointmentsTable(List<Appointment> appointments) {
        DefaultTableModel model = (DefaultTableModel) view.getAppointmentsTable().getModel();
        model.setRowCount(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Appointment app : appointments) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(app.getId()));
            row.add(app.getDoctor().getFirstName() + " " + app.getDoctor().getLastName());
            row.add(app.getPatient().getFirstName() + " " + app.getPatient().getLastName());
            row.add(dateFormat.format(app.getAppointedDate()));
            row.add(app.getAppointedTime());
            row.add(app.getStatus().toString());
            model.addRow(row);
        }
    }

    private void selectAppointmentFromTable(int row) {
        if (row >= 0 && row < allAppointments.size()) {
            Appointment appointment = allAppointments.get(row);
            populateFormFromAppointment(appointment);
            view.updateStatus("Selected appointment for modification", new Color(230, 126, 34));
        }
    }

    private void populateFormFromAppointment(Appointment appointment) {
        String doctorName = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();
        String patientName = appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName();

        for (int i = 0; i < view.getDoctorComboBox().getItemCount(); i++) {
            if (view.getDoctorComboBox().getItemAt(i).equals(doctorName)) {
                view.getDoctorComboBox().setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < view.getPatientComboBox().getItemCount(); i++) {
            if (view.getPatientComboBox().getItemAt(i).equals(patientName)) {
                view.getPatientComboBox().setSelectedIndex(i);
                break;
            }
        }

        view.getDateField().setDate(appointment.getAppointedDate());
        view.getTimeField().setValue(appointment.getAppointedTime());
    }

    private boolean validateAppointmentData() {
        if (view.getDoctorComboBox().getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "Please select a doctor.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (view.getPatientComboBox().getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "Please select a patient.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (view.getDateField().getDate() == null) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "Please enter appointment date.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (view.getTimeField().getValue() == null) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "Please enter appointment time.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public void showView() {
        viewPanel.removeAll();
        viewPanel.add(view);
        SwingUtilities.updateComponentTreeUI(viewPanel);
    }
}
