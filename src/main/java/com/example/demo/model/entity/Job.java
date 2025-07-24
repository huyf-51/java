package com.example.demo.model.entity;

import com.example.demo.model.enums.JobType;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private JobType type;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User employer;

    @OneToMany(mappedBy = "job")
    private List<Application> applicationList;

    @OneToOne(mappedBy = "job")
    private Job_Detail jobDetail;
}
