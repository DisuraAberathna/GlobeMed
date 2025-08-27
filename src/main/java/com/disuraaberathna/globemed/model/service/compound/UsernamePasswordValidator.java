package com.disuraaberathna.globemed.model.service.compound;

import com.disuraaberathna.globemed.model.dao.SignInDAO;
import com.disuraaberathna.globemed.model.entity.User;
import com.disuraaberathna.globemed.util.HibernateUtil;
import com.disuraaberathna.globemed.util.LoggerUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UsernamePasswordValidator extends SignInController {
    private final static Logger logger = LoggerUtil.getLogger();

    @Override
    public SignInDAO handleRequest(SignInDAO signInDAO) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
            Root<User> userRoot = criteriaQuery.from(User.class);

            criteriaQuery.where(cb.and(
                    cb.equal(userRoot.get("username"), signInDAO.getUsername()),
                    cb.equal(userRoot.get("password"), signInDAO.getPassword())
            ));

            User user = session.createQuery(criteriaQuery).uniqueResult();

            if (user != null) {
                signInDAO.setUser(user);
                transaction.commit();

                if (signInController != null) {
                    signInController.handleRequest(signInDAO);
                }
            } else {
                signInDAO.setMessage("Username or password is incorrect");
                return signInDAO;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            logger.log(Level.SEVERE, e.getMessage(), e);
            signInDAO.setMessage("Try Again");
            return signInDAO;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return signInDAO;
    }
}
