package com.disuraaberathna.globemed.model.dao;

import com.disuraaberathna.globemed.model.entity.ReportItem;
import com.disuraaberathna.globemed.util.HibernateUtil;
import com.disuraaberathna.globemed.util.LoggerUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportItemDAO {
    private final static Logger logger = LoggerUtil.getLogger();

    public void saveReportItems(List<ReportItem> items) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            for (ReportItem reportItem : items) {
                session.persist(reportItem);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            logger.log(Level.SEVERE, "Exception in ReportItemDAO.saveReportItems" + e.getMessage());
        }
    }
}
