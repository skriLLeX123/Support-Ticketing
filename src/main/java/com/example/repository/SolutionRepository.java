package com.example.repository;

import com.example.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {

    /**
     * Find solution by name
     */
    Optional<Solution> findByName(String name);

    /**
     * Check if solution exists by name
     */
    boolean existsByName(String name);

    /**
     * Find solutions by name containing (case-insensitive)
     */
    List<Solution> findByNameContainingIgnoreCase(String name);
} 