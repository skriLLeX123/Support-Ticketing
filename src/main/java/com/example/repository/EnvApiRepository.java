package com.example.repository;

import com.example.entity.EnvApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnvApiRepository extends JpaRepository<EnvApi, UUID> {
    
    List<EnvApi> findBySolutionEnvironmentId(UUID solutionEnvironmentId);
    
    List<EnvApi> findByApiId(UUID apiId);
    
    List<EnvApi> findBySolutionEnvironment_SolutionId(UUID solutionId);
    
    List<EnvApi> findBySolutionEnvironment_EnvironmentId(Long environmentId);
} 