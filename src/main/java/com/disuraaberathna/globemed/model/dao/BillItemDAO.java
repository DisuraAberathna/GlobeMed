package com.disuraaberathna.globemed.model.dao;

import com.disuraaberathna.globemed.model.entity.BillItem;
import com.disuraaberathna.globemed.util.HibernateUtil;
import com.disuraaberathna.globemed.util.LoggerUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillItemDAO {
    private final static Logger logger = LoggerUtil.getLogger();

    public void saveBillItem(BillItem billItem) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(billItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void saveBillItems(List<BillItem> billItems) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            for (BillItem item : billItems) {
                session.persist(item);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
