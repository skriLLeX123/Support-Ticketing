package com.example.repository;

import com.example.entity.EnvApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnvApiRepository extends JpaRepository<EnvApi, UUID> {
    
    List<EnvApi> findBySolutionEnvironmentId(UUID solutionEnvironmentId);
    
    List<EnvApi> findByApiId(UUID apiId);
    
    List<EnvApi> findBySolutionEnvironment_SolutionId(UUID solutionId);
    
    List<EnvApi> findBySolutionEnvironment_EnvironmentId(Long environmentId);
    
    @Query("SELECT DISTINCT p.name, p.logoUrl FROM EnvApi ea " +
           "JOIN ea.solutionEnvironment se " +
           "JOIN se.solution s " +
           "JOIN s.account a " +
           "JOIN a.partner p " +
           "WHERE ea.api.id = :apiId " +
           "ORDER BY p.name")
    List<Object[]> findPartnerInfoByApiId(@Param("apiId") UUID apiId);
} 