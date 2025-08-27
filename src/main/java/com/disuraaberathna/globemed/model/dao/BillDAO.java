package com.disuraaberathna.globemed.model.dao;

import com.disuraaberathna.globemed.model.entity.Bill;
import com.disuraaberathna.globemed.util.HibernateUtil;
import com.disuraaberathna.globemed.util.LoggerUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillDAO {
    private final static Logger logger = LoggerUtil.getLogger();

    public void saveBill(Bill bill) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(bill);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void updateBill(Bill bill) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(bill);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public Bill getBillById(int billId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Bill.class, billId);
        }
    }

    public List<Bill> getBillsByPatient(int patientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Bill where appointment.patient.id = :patientId", Bill.class)
                    .setParameter("patientId", patientId)
                    .getResultList();
        }
    }
}
