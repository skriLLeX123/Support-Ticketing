package com.example.repository;

import com.example.entity.Api;
import com.example.entity.Environment;
import com.example.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApiRepository extends JpaRepository<Api, UUID> {
    
    List<Api> findByActiveTrue();
    
    @Query("SELECT a FROM Api a WHERE a.active = true ORDER BY a.name")
    List<Api> findAllActiveApis();
    
    // Find APIs by environment and solution through EnvApi relationship
    @Query("SELECT DISTINCT a FROM Api a JOIN a.envApis ea WHERE ea.solutionEnvironment.environment = :environment AND ea.solutionEnvironment.solution = :solution")
    List<Api> findByEnvironmentAndSolution(Environment environment, Solution solution);
    
    // Find active APIs by environment through EnvApi relationship
    @Query("SELECT DISTINCT a FROM Api a JOIN a.envApis ea WHERE ea.solutionEnvironment.environment = :environment AND a.active = true")
    List<Api> findByEnvironmentAndActiveTrue(Environment environment);
    
    // Find active APIs by solution through EnvApi relationship
    @Query("SELECT DISTINCT a FROM Api a JOIN a.envApis ea WHERE ea.solutionEnvironment.solution = :solution AND a.active = true")
    List<Api> findBySolutionAndActiveTrue(Solution solution);
}