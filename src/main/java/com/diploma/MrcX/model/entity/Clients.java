package com.diploma.MrcX.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Clients {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

}