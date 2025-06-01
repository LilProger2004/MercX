package com.diploma.MrcX.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    public enum OrderStatus {
        NEW("Новый"), IN_PROGRESS("В процессе"), COMPLETED("Завершен"), CANCELLED("Отменен");

        public final String label;

        OrderStatus(String label) {
            this.label = label;
        }
    }

    @Id
    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 255)
    @JsonProperty("name")
    private String name;

    @Column(columnDefinition = "TEXT")
    @JsonProperty("description")
    private String description;

    @Column(name = "price")
    @JsonProperty("price")
    private double price;

    @ManyToOne
    private Freelancers freelancer;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    List<Offers> offers;

    @ManyToOne
    private Clients client;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categori;

    @Enumerated(EnumType.STRING)
    @Column( length = 50)
    @Builder.Default
    private OrderStatus status = OrderStatus.NEW;

    @Column(name = "deadline")
    @JsonProperty("deadline")
    private long deadline;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Files> files;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CommentFiles> commentFiles;

    @Column(name = "solution_comment")
    private String solutionComment;

    @OneToMany
    private List<Skills> skills;

    // Дополнительные методы для бизнес-логики
    public boolean isCompleted() {
        return status == OrderStatus.COMPLETED;
    }
}
