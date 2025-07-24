package com.example.demo.repository;

import com.example.demo.model.entity.Employer_Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployerProfileRepository extends JpaRepository<Employer_Profile, UUID> {

}
