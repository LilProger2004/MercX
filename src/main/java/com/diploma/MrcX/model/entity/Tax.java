package com.diploma.MrcX.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tax")
public class Tax {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "sum")
    private double sum;

}