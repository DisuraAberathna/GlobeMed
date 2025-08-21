package com.disuraaberathna.globemed.controller.signin;

import com.disuraaberathna.globemed.enums.Status;
import com.disuraaberathna.globemed.model.dao.SignInDAO;

public class UserStatusValidator extends SignInController {

    @Override
    public SignInDAO handleRequest(SignInDAO signInDAO) {
        if (signInDAO.getUser().getStatus() == Status.ACTIVE) {
            signInDAO.setMessage("true");

            if (signInController != null) {
                signInController.handleRequest(signInDAO);
            }
        } else {
            signInDAO.setMessage("This User Account is Banned");
            return signInDAO;
        }

        return signInDAO;
    }
}
