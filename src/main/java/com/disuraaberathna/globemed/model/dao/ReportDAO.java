package com.disuraaberathna.globemed.model.dao;

import com.disuraaberathna.globemed.model.entity.Report;
import com.disuraaberathna.globemed.util.HibernateUtil;
import com.disuraaberathna.globemed.util.LoggerUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportDAO {
    private final static Logger logger = LoggerUtil.getLogger();

    public void saveReport(Report report) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(report);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            logger.log(Level.SEVERE, "Exception in ReportDAO.saveReport" + e.getMessage());
        }
    }
}
