package com.disuraaberathna.globemed.model.service.brigde;

import com.disuraaberathna.globemed.model.entity.Bill;

import javax.swing.*;

public abstract class BillingSystem {
    protected BillingImplementor billingImplementor;

    public BillingSystem(BillingImplementor billingImplementor) {
        this.billingImplementor = billingImplementor;
    }

    public abstract void generateBill(Bill bill,JFrame frame);

    public void processPayment(Bill bill, JFrame frame) {
        billingImplementor.processPayment(bill, frame);
    }
}
