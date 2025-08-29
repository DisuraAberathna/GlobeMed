package com.disuraaberathna.globemed.model.dao;

import com.disuraaberathna.globemed.model.entity.Insurance;
import com.disuraaberathna.globemed.util.HibernateUtil;
import com.disuraaberathna.globemed.util.LoggerUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsuranceDAO {
    private final static Logger logger = LoggerUtil.getLogger();

    public Insurance saveInsurance(Insurance insurance) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(insurance);
            transaction.commit();

            return insurance;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            logger.log(Level.SEVERE, "Exception in InsuranceDAO.saveInsurance" + e.getMessage());

            return null;
        }
    }

    public Insurance getInsurance(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Insurance.class, id);
        }
    }

    public Insurance findByProviderAndPolicy(String provider, String policyNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Insurance> results = session.createQuery(
                            "from Insurance where provider = :provider and policyNumber = :policyNumber",
                            Insurance.class
                    )
                    .setParameter("provider", provider)
                    .setParameter("policyNumber", policyNumber)
                    .list();
            return results.isEmpty() ? null : results.get(0);
        }
    }

    public Insurance findOrCreate(String provider, String policyNumber) {
        Insurance existing = findByProviderAndPolicy(provider, policyNumber);

        if (existing != null) {
            return existing;
        }

        Insurance insurance = new Insurance(provider, policyNumber);
        return saveInsurance(insurance);
    }
}
