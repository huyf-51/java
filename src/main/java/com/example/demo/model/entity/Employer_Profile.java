package com.example.demo.model.entity;

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
public class Employer_Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fullname;
    private String intro;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user_employer;
}
