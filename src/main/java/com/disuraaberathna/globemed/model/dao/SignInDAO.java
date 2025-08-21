package com.disuraaberathna.globemed.model.dao;

import com.disuraaberathna.globemed.enums.UserRoles;
import com.disuraaberathna.globemed.model.entity.User;

public class SignInDAO {
    private final String username;
    private final String password;
    private User user;
    private UserRoles userRole;
    private boolean success = false;
    private String message;

    public SignInDAO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
