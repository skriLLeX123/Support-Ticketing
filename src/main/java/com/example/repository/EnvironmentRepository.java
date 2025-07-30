package com.example.repository;

import com.example.entity.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
    
    Optional<Environment> findByType(Environment.EnvironmentType type);
    
    List<Environment> findByActiveTrue();
    
    @Query("SELECT e FROM Environment e WHERE e.active = true ORDER BY e.type")
    List<Environment> findAllActiveEnvironments();
    
    boolean existsByType(Environment.EnvironmentType type);
}