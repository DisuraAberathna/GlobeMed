package com.disuraaberathna.globemed.model.service.brigde;

import com.disuraaberathna.globemed.enums.Status;
import com.disuraaberathna.globemed.model.entity.Bill;

import javax.swing.*;

public class InsuranceBilling implements BillingImplementor {
    @Override
    public void processPayment(Bill bill, JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Processing insurance claim for bill : " + bill.getId(), "Information", JOptionPane.INFORMATION_MESSAGE);
        bill.setStatus(Status.CLAIM_SUBMITTED);
    }
}
