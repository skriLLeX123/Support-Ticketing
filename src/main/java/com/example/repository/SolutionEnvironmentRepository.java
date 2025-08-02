package com.example.repository;

import com.example.entity.SolutionEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionEnvironmentRepository extends JpaRepository<SolutionEnvironment, UUID> {
    
    List<SolutionEnvironment> findBySolution_SolutionId(UUID solutionId);
    
    List<SolutionEnvironment> findByEnvironment_Id(Long environmentId);
    
    SolutionEnvironment findBySolution_SolutionIdAndEnvironment_Id(UUID solutionId, Long environmentId);
} 