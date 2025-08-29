package com.disuraaberathna.globemed.controller;

import com.disuraaberathna.globemed.enums.Status;
import com.disuraaberathna.globemed.model.dao.AppointmentDAO;
import com.disuraaberathna.globemed.model.dao.BillDAO;
import com.disuraaberathna.globemed.model.dao.PatientDAO;
import com.disuraaberathna.globemed.model.entity.Appointment;
import com.disuraaberathna.globemed.model.entity.Bill;
import com.disuraaberathna.globemed.model.entity.Patient;
import com.disuraaberathna.globemed.model.service.brigde.BillingSystem;
import com.disuraaberathna.globemed.model.service.brigde.DirectBilling;
import com.disuraaberathna.globemed.model.service.brigde.InsuranceBilling;
import com.disuraaberathna.globemed.model.service.brigde.StandardBillingSystem;
import com.disuraaberathna.globemed.util.LoggerUtil;
import com.disuraaberathna.globemed.view.BillingView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillingController {
    private final BillingView view;
    private final PatientDAO patientDAO;
    private final BillDAO billDAO;
    private final AppointmentDAO appointmentDAO;
    private List<Patient> patientList;
    private List<Appointment> appointmentList;
    private List<Bill> billList;
    private Bill currentBill;
    private final JPanel viewPanel;
    private final static Logger logger = LoggerUtil.getLogger();

    public BillingController(BillingView view, PatientDAO patientDAO, BillDAO billDAO, AppointmentDAO appointmentDAO, JPanel viewPanel) {
        this.view = view;
        this.patientDAO = patientDAO;
        this.billDAO = billDAO;
        this.appointmentDAO = appointmentDAO;
        this.viewPanel = viewPanel;

        loadInitialData();
        addEventListeners();
    }

    private void loadInitialData() {
        patientList = patientDAO.getAllPatients();
        appointmentList = appointmentDAO.getAllAppointments();
        billList = billDAO.getAllBills();

        view.getPatientComboBox().removeAllItems();
        view.getPatientComboBox().addItem("Select Patient");

        for (Patient patient : patientList) {
            view.getPatientComboBox().addItem(patient.getFirstName() + " " + patient.getLastName());
        }

        loadBillsTable();
    }

    private void addEventListeners() {
        view.getGenerateBtn().addActionListener(e -> generateBill());

        view.getProcessBtn().addActionListener(e -> processBill());

        view.getClearBtn().addActionListener(e -> clearForm());

        view.getSearchField().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchBills();
            }
        });

        view.getPatientComboBox().addActionListener(e -> {
            if (view.getPatientComboBox().getSelectedIndex() > 0) {
                updateAppointmentsForPatient();
            }
        });

        view.getAppointmentComboBox().addActionListener(e -> {
            if (view.getAppointmentComboBox().getSelectedIndex() > 0) {
                updateBillAmount();
            }
        });
    }

    private void generateBill() {
        try {
            if (view.getPatientComboBox().getSelectedIndex() <= 0) {
                view.updateStatus("Please select a patient", Color.RED);
                return;
            }

            if (view.getAppointmentComboBox().getSelectedIndex() <= 0) {
                view.updateStatus("Please select an appointment", Color.RED);
                return;
            }

            Appointment selectedAppointment = appointmentList.get(view.getAppointmentComboBox().getSelectedIndex() - 1);
            String paymentType = (String) view.getTypeComboBox().getSelectedItem();
            Double amount = Double.parseDouble(view.getTotalField().getText());

            currentBill = new Bill();
            currentBill.setAmount(amount);
            currentBill.setDate(new Date());
            currentBill.setAppointment(selectedAppointment);
            currentBill.setStatus(Status.PENDING);

            BillingSystem billingSystem = new StandardBillingSystem(
                    "Direct".equals(paymentType) ? new DirectBilling() : new InsuranceBilling()
            );

            billingSystem.generateBill(currentBill, (JFrame) SwingUtilities.getWindowAncestor(view));

            billDAO.saveBill(currentBill);

            view.updateStatus("Bill generated successfully! Bill ID: " + currentBill.getId(), Color.GREEN);
            clearForm();
            loadBillsTable();
        } catch (NumberFormatException ex) {
            view.updateStatus("Please enter a valid amount", Color.RED);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error generating bill: " + ex.getMessage());
            view.updateStatus("Error generating bill", Color.RED);
        }
    }

    private void processBill() {
        try {
            int selectedRow = view.getBillsTable().getSelectedRow();
            if (selectedRow == -1) {
                view.updateStatus("Please select a bill to process", Color.RED);
                return;
            }

            Integer billId = (Integer) view.getBillsTable().getValueAt(selectedRow, 0);
            Bill bill = billDAO.getBillById(billId);

            if (bill == null) {
                view.updateStatus("Bill not found", Color.RED);
                return;
            }

            String paymentType = (String) view.getTypeComboBox().getSelectedItem();

            BillingSystem billingSystem = new StandardBillingSystem(
                    "Direct".equals(paymentType) ? new DirectBilling() : new InsuranceBilling()
            );

            billingSystem.processPayment(bill, (JFrame) SwingUtilities.getWindowAncestor(view));

            billDAO.updateBill(bill);

            view.updateStatus("Payment processed successfully!", Color.GREEN);
            loadBillsTable();

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error processing bill: " + ex.getMessage());
            view.updateStatus("Error processing bill", Color.RED);
        }
    }

    private void updateAppointmentsForPatient() {
        Patient selectedPatient = patientList.get(view.getPatientComboBox().getSelectedIndex() - 1);

        view.getAppointmentComboBox().removeAllItems();
        view.getAppointmentComboBox().addItem("Select Appointment");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        for (Appointment appointment : appointmentList) {
            if (appointment.getPatient().getId().equals(selectedPatient.getId())) {
                try {
                    Date date = inputFormat.parse(appointment.getAppointedTime());
                    String formattedDate = dateFormat.format(appointment.getAppointedDate());
                    String formattedTime = timeFormat.format(date);

                    view.getAppointmentComboBox().addItem("Appt #" + appointment.getId() + " - " +
                            formattedDate + " " + formattedTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void updateBillAmount() {
        try {
            double amount = 5000.00;
            view.getTotalField().setText(String.valueOf(amount));
        } catch (Exception ex) {
            view.getTotalField().setText("0.00");
        }
    }

    private void searchBills() {
        String searchTerm = view.getSearchField().getText().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) view.getBillsTable().getModel();
        model.setRowCount(0);

        for (Bill bill : billList) {
            if (bill.getAppointment().getPatient().getFirstName().toLowerCase().contains(searchTerm) ||
                    bill.getAppointment().getPatient().getLastName().toLowerCase().contains(searchTerm) ||
                    bill.getId().toString().contains(searchTerm)) {

                model.addRow(new Object[]{
                        bill.getId(),
                        bill.getAppointment().getDoctor().getFirstName() + " " + bill.getAppointment().getDoctor().getLastName(),
                        bill.getAppointment().getPatient().getFirstName() + " " + bill.getAppointment().getPatient().getLastName(),
                        bill.getDate(),
                        "LKR " + bill.getAmount(),
                        bill.getStatus()
                });
            }
        }
    }

    private void loadBillsTable() {
        billList = billDAO.getAllBills();
        DefaultTableModel model = (DefaultTableModel) view.getBillsTable().getModel();
        model.setRowCount(0);

        for (Bill bill : billList) {
            model.addRow(new Object[]{
                    bill.getId(),
                    bill.getAppointment().getDoctor().getFirstName() + " " + bill.getAppointment().getDoctor().getLastName(),
                    bill.getAppointment().getPatient().getFirstName() + " " + bill.getAppointment().getPatient().getLastName(),
                    bill.getDate(),
                    "LKR " + bill.getAmount(),
                    bill.getStatus()
            });
        }
    }

    private void clearForm() {
        view.getPatientComboBox().setSelectedIndex(0);
        view.getAppointmentComboBox().setSelectedIndex(0);
        view.getTypeComboBox().setSelectedIndex(0);
        view.getTotalField().setText("");
        view.updateStatus("Ready to generate bill", Color.BLUE);
    }


    public void showView() {
        viewPanel.removeAll();
        viewPanel.add(view);
        SwingUtilities.updateComponentTreeUI(viewPanel);
    }
}
