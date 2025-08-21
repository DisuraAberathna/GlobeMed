package com.disuraaberathna.globemed.controller.signin;

import com.disuraaberathna.globemed.model.dao.SignInDAO;

public abstract class SignInController {
    protected SignInController signInController;

    public void setNextHandler(SignInController signInController) {
        this.signInController = signInController;
    }

    public abstract SignInDAO handleRequest(SignInDAO signInDAO);
}
