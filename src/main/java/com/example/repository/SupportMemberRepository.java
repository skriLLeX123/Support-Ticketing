package com.example.repository;

import com.example.entity.SupportMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupportMemberRepository extends JpaRepository<SupportMember, UUID> {

    /**
     * Find support member by email
     */
    Optional<SupportMember> findByEmail(String email);

    /**
     * Find support member by name
     */
    Optional<SupportMember> findByName(String name);

    /**
     * Check if support member exists by email
     */
    boolean existsByEmail(String email);

    /**
     * Find support members by name containing (case-insensitive)
     */
    List<SupportMember> findByNameContainingIgnoreCase(String name);

    /**
     * Find support members by support group
     */
    List<SupportMember> findBySupportGroup_SupportGroupId(UUID supportGroupId);

    /**
     * Find support members by support group name
     */
    List<SupportMember> findBySupportGroup_Name(String supportGroupName);
} 