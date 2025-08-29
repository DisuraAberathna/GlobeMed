package com.disuraaberathna.globemed.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table
public class Insurance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String provider;
    private String policyNumber;

    public Insurance() {
    }

    public Insurance(String provider, String policyNumber) {
        this.provider = provider;
        this.policyNumber = policyNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
}
