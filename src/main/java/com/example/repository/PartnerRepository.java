package com.example.repository;

import com.example.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, UUID> {
    Optional<Partner> findByName(String name);
} 