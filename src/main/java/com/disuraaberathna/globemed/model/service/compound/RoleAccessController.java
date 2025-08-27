package com.disuraaberathna.globemed.model.service.compound;

import com.disuraaberathna.globemed.model.dao.SignInDAO;

public class RoleAccessController extends SignInController {
    @Override
    public SignInDAO handleRequest(SignInDAO signInDAO) {
        signInDAO.setUserRole(signInDAO.getUser().getRole());
        signInDAO.setSuccess(true);
        return signInDAO;
    }
}
