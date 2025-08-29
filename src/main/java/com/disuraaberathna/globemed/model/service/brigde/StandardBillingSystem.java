package com.disuraaberathna.globemed.model.service.brigde;

import com.disuraaberathna.globemed.enums.Status;
import com.disuraaberathna.globemed.model.entity.Bill;

import javax.swing.*;

public class StandardBillingSystem extends BillingSystem {
    public StandardBillingSystem(BillingImplementor billingImplementor) {
        super(billingImplementor);
    }

    @Override
    public void generateBill(Bill bill, JFrame frame) {
        JOptionPane.showMessageDialog(
                frame,
                "Generating bill for " + bill.getAppointment().getPatient().getFirstName() + " " + bill.getAppointment().getPatient().getLastName() + " - Appt #" + bill.getAppointment().getId(),
                "Information",
                JOptionPane.INFORMATION_MESSAGE
        );

        bill.setAmount(5000.00);
        bill.setStatus(Status.GENERATED);
    }
}
