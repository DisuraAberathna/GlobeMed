package com.disuraaberathna.globemed.model.dao;

import com.disuraaberathna.globemed.model.entity.Patient;
import com.disuraaberathna.globemed.util.HibernateUtil;
import com.disuraaberathna.globemed.util.LoggerUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientDAO {
    private final static Logger logger = LoggerUtil.getLogger();

    public void savePatient(Patient patient) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(patient);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void updatePatient(Patient patient) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(patient);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public Patient getPatient(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Patient.class, id);
        }
    }

    public List<Patient> getAllPatients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Patient", Patient.class).getResultList();
        }
    }
}
