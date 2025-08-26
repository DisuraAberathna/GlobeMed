package com.disuraaberathna.globemed.controller.signin;

import com.disuraaberathna.globemed.model.dao.SignInDAO;

import java.util.Base64;

public class EncryptionSignInDecorator extends SignInControllerDecorator {
    public EncryptionSignInDecorator(SignInController signInController) {
        super(signInController);
    }

    @Override
    public SignInDAO handleRequest(SignInDAO signInDAO) {
        String encryptedPassword = Base64.getEncoder().encodeToString(signInDAO.getPassword().getBytes());
        SignInDAO encryptedDAO = new SignInDAO(signInDAO.getUsername(), encryptedPassword);
        return super.handleRequest(encryptedDAO);
    }
}
