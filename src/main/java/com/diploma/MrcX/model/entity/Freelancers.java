package com.diploma.MrcX.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "freelancers")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Freelancers {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @JsonProperty("username")
    @Column(name = "name")
    private String name;

    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "balance")
    private double balance;

    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "about_me")
    @JsonProperty("description")
    private String aboutMe;

    @ManyToOne
    @JoinColumn(name = "category_id")
     private Category category;
    
    @OneToMany(mappedBy = "freelancer" , cascade = CascadeType.ALL)
    List<Offers> offers;

    @Column(name = "price_in_hour")
    private double priceInHour;

    @OneToMany(mappedBy = "freelancer")
    private List<Order> orders;

    @OneToMany
    private List<Skills> skills;

    @Column(name = "freelancer_rating")
    private Short freelancerRating;

}