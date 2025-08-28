package com.disuraaberathna.globemed.model.entity;

import com.disuraaberathna.globemed.enums.Status;
import com.disuraaberathna.globemed.enums.UserRoles;
import com.disuraaberathna.globemed.enums.UserTitles;
import com.disuraaberathna.globemed.model.service.composite.StaffMember;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable, StaffMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private UserTitles title;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRoles role;
    @Enumerated(EnumType.STRING)
    private Status status;

    public User() {
    }

    public User(UserTitles title, String firstName, String lastName, String username, String password, UserRoles role) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserTitles getTitle() {
        return title;
    }

    public void setTitle(UserTitles title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String showDetails() {
        return "User : " + getTitle() + " " + getFirstName() + " " + getLastName() + ", Role : " + getRole();
    }

    @Override
    public String getName() {
        return getFirstName() + " " + getLastName();
    }
}
