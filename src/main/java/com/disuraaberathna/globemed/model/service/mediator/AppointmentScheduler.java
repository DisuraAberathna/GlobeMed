package com.disuraaberathna.globemed.model.service.mediator;

import com.disuraaberathna.globemed.enums.Status;
import com.disuraaberathna.globemed.model.dao.AppointmentDAO;
import com.disuraaberathna.globemed.model.entity.Appointment;
import com.disuraaberathna.globemed.model.entity.Patient;
import com.disuraaberathna.globemed.model.entity.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentScheduler implements ScheduleMediator {
    private final List<User> doctors = new ArrayList<>();
    private final List<Patient> patients = new ArrayList<>();
    private final AppointmentDAO appointmentDAO;
    private final JFrame container;

    public AppointmentScheduler(AppointmentDAO appointmentDAO, JFrame container) {
        this.appointmentDAO = appointmentDAO;
        this.container = container;
    }

    public void addDoctor(User user) {
        doctors.add(user);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    @Override
    public void schedule(Appointment appointment) {
        if (isConflict(appointment)) {
            JOptionPane.showMessageDialog(container, "Conflict detected for appointment " + appointment.getId(), "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            appointmentDAO.saveAppointment(appointment);
            JOptionPane.showMessageDialog(container, "Appointment scheduled " + appointment.getId(), "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void reschedule(Appointment appointment) {
        appointmentDAO.updateAppointment(appointment);
        JOptionPane.showMessageDialog(container, "Appointment rescheduled " + appointment.getId(), "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void cancel(Appointment appointment) {
        appointment.setStatus(Status.CANCELLED);
        appointmentDAO.updateAppointment(appointment);
        JOptionPane.showMessageDialog(container, "Appointment canceled " + appointment.getId(), "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean isConflict(Appointment newAppointment) {
        List<Appointment> doctorAppointments = appointmentDAO.getAppointmentsByDoctor(newAppointment.getDoctor().getId());

        for (Appointment existingAppointment : doctorAppointments) {
            if (existingAppointment.getAppointedDate().equals(newAppointment.getAppointedDate()) &&
                    isTimeOverlap(existingAppointment, newAppointment)) {
                return true;
            }
        }

        return false;
    }

    private boolean isTimeOverlap(Appointment newAppointment, Appointment existingAppointment) {
        return existingAppointment.getAppointedTime().equals(newAppointment.getAppointedTime());
    }

    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        return appointmentDAO.getAppointmentsByDoctor(doctorId);
    }
}
