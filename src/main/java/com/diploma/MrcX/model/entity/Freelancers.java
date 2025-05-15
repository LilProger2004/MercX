package com.diploma.MrcX.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "freelancers")
public class Freelancers {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @OneToMany(mappedBy = "freelancer")
    private List<Order> orders;

    @Column(name = "freelancer_rating")
    private Short freelancerRating;

}