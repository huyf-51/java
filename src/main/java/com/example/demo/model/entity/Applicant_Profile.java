package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "applicant_profiles", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Applicant_Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fullname;
    private String phoneNumber;
    private String email;
    private String resume;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user_applicant;
}
