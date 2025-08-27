package com.disuraaberathna.globemed.model.service.compound;

import com.disuraaberathna.globemed.model.dao.SignInDAO;
import com.disuraaberathna.globemed.util.LoggerUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingSignInDecorator extends SignInControllerDecorator {
    private static final Logger logger = LoggerUtil.getLogger();

    public LoggingSignInDecorator(SignInController signInController) {
        super(signInController);
    }

    @Override
    public SignInDAO handleRequest(SignInDAO signInDAO) {
        logger.log(Level.INFO, "Sign in attempt for user : " + signInDAO.getUsername());

        SignInDAO response = super.handleRequest(signInDAO);

        if (response.isSuccess()) {
            logger.log(Level.INFO, "Sign in success for user : " + signInDAO.getUsername());
        } else {
            logger.log(Level.WARNING, "Sign in failed for user : " + signInDAO.getUsername());
        }

        return response;
    }
}
