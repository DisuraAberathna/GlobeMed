package com.disuraaberathna.globemed.model.dao;

import com.disuraaberathna.globemed.model.entity.MedicalReport;
import com.disuraaberathna.globemed.util.HibernateUtil;
import com.disuraaberathna.globemed.util.LoggerUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicalReportDAO {
    private final static Logger logger = LoggerUtil.getLogger();

    public void saveMedicalReport(MedicalReport medicalReport) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(medicalReport);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public List<MedicalReport> getMedicalReportsByPatientId(int patientId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from MedicalReport where patient.id = :patientId", MedicalReport.class)
                    .setParameter("patientId", patientId)
                    .getResultList();
        }
    }
}
