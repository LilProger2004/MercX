package com.diploma.MrcX.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "computing_procent")
public class ComputingProcent {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "percent")
    private double percent;
}