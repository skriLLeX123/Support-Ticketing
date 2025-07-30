package com.example.repository;

import com.example.entity.Api;
import com.example.entity.Environment;
import com.example.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiRepository extends JpaRepository<Api, Long> {
    
    List<Api> findByEnvironment(Environment environment);
    
    List<Api> findBySolution(Solution solution);
    
    List<Api> findByEnvironmentAndActiveTrue(Environment environment);
    
    List<Api> findBySolutionAndActiveTrue(Solution solution);
    
    @Query("SELECT a FROM Api a WHERE a.environment = :environment AND a.solution = :solution AND a.active = true")
    List<Api> findByEnvironmentAndSolution(@Param("environment") Environment environment, @Param("solution") Solution solution);
    
    @Query("SELECT a FROM Api a WHERE a.active = true ORDER BY a.name")
    List<Api> findAllActiveApis();
}