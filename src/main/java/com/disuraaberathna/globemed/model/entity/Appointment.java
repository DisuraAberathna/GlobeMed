package com.disuraaberathna.globemed.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "appointments")
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
