package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "jobId", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User applicant;
}
