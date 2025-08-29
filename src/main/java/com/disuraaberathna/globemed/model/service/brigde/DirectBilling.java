package com.disuraaberathna.globemed.model.service.brigde;

import com.disuraaberathna.globemed.enums.Status;
import com.disuraaberathna.globemed.model.entity.Bill;

import javax.swing.*;

public class DirectBilling implements BillingImplementor {
    @Override
    public void processPayment(Bill bill, JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Processing direct payment for bill : " + bill.getId(), "Information", JOptionPane.INFORMATION_MESSAGE);
        bill.setStatus(Status.PAID);
    }
}
