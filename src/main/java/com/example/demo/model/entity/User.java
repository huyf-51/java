package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import com.example.demo.model.enums.Role;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user_employer")
    private Employer_Profile employer_profile;

    @OneToOne(mappedBy = "user_applicant")
    private Applicant_Profile applicant_profile;

    @OneToMany(mappedBy = "applicant")
    private List<Application> applicationList;

    @OneToMany(mappedBy = "employer")
    private List<Job> jobList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}