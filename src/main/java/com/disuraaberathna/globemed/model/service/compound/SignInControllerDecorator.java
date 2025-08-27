package com.disuraaberathna.globemed.model.service.compound;

import com.disuraaberathna.globemed.model.dao.SignInDAO;

public abstract class SignInControllerDecorator extends SignInController {
    private final SignInController signInController;

    public SignInControllerDecorator(SignInController signInController) {
        this.signInController = signInController;
    }

    @Override
    public SignInDAO handleRequest(SignInDAO signInDAO) {
        return signInController.handleRequest(signInDAO);
    }
}
