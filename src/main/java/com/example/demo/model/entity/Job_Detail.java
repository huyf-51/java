package com.example.demo.model.entity;

import com.example.demo.model.enums.Experience;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "employer_profiles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long salary;

    private String location;

    private Experience experience;

    private Integer headcount;

    @OneToOne
    @JoinColumn(name = "jobId")
    private Job job;
}
