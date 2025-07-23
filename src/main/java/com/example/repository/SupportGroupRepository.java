package com.example.repository;

import com.example.entity.SupportGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupportGroupRepository extends JpaRepository<SupportGroup, UUID> {

    /**
     * Find support group by name
     */
    Optional<SupportGroup> findByName(String name);

    /**
     * Check if support group exists by name
     */
    boolean existsByName(String name);

    /**
     * Find support groups by name containing (case-insensitive)
     */
    List<SupportGroup> findByNameContainingIgnoreCase(String name);
} 