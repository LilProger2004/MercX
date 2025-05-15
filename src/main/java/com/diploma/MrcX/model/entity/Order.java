package com.diploma.MrcX.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    public enum OrderStatus {
        NEW, IN_PROGRESS, COMPLETED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 255)
    private String price;

    @ManyToOne
    private Freelancers freelancer;

    @ManyToOne
    private Clients client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @Builder.Default
    private OrderStatus status = OrderStatus.NEW;

    @Column(name = "deadline", nullable = false, updatable = false)
    private int deadline;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(length = 100)
    private String category;

    @ElementCollection
    @CollectionTable(name = "order_attachments", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "attachment_url")
    private List<String> attachments;

    // Дополнительные методы для бизнес-логики
    public boolean isCompleted() {
        return status == OrderStatus.COMPLETED;
    }
}
