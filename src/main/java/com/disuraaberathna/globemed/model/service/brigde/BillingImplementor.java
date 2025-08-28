package com.disuraaberathna.globemed.model.service.brigde;

import com.disuraaberathna.globemed.model.entity.Bill;

import javax.swing.*;

public interface BillingImplementor {
    void processPayment(Bill bill, JFrame frame);
}
