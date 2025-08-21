package com.disuraaberathna.globemed.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table
public class Insurance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double claim_amount;
    private String description;

    public Insurance() {
    }

    public Insurance(Double claim_amount, String description) {
        this.claim_amount = claim_amount;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getClaim_amount() {
        return claim_amount;
    }

    public void setClaim_amount(Double claim_amount) {
        this.claim_amount = claim_amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
