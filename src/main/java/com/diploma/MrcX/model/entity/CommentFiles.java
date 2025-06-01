package com.diploma.MrcX.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String filePath;

    @ManyToOne
    private Order order;
}
