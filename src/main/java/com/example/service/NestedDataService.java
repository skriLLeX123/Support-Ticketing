package com.example.service;

import com.example.dto.NestedDataDTO;
import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NestedDataService {
    
    @Autowired
    private PartnerRepository partnerRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private SolutionRepository solutionRepository;
    
    @Autowired
    private EnvironmentRepository environmentRepository;
    
    @Autowired
    private ApiRepository apiRepository;
    
    public List<NestedDataDTO.PartnerDTO> getNestedDataStructure() {
        List<NestedDataDTO.PartnerDTO> partners = new ArrayList<>();
        
        // Get all partners
        List<Partner> allPartners = partnerRepository.findAll();
        
        for (Partner partner : allPartners) {
            NestedDataDTO.PartnerDTO partnerDTO = buildPartnerDTO(partner);
            partners.add(partnerDTO);
        }
        
        return partners;
    }
    
    private NestedDataDTO.PartnerDTO buildPartnerDTO(Partner partner) {
        NestedDataDTO.PartnerDTO partnerDTO = new NestedDataDTO.PartnerDTO(
            partner.getPartnerId().toString(),
            partner.getName(),
            partner.getDescription(),
            getPartnerLogo(partner.getName())
        );
        
        // Get accounts for this partner
        List<Account> accounts = accountRepository.findByPartner(partner);
        List<NestedDataDTO.AccountDTO> accountDTOs = new ArrayList<>();
        
        int totalSolutions = 0;
        int totalEnvironments = 0;
        int totalApis = 0;
        
        for (Account account : accounts) {
            NestedDataDTO.AccountDTO accountDTO = buildAccountDTO(account);
            accountDTOs.add(accountDTO);
            
            // Aggregate stats
            totalSolutions += accountDTO.getStats().getSolutionCount();
            totalEnvironments += accountDTO.getStats().getEnvironmentCount();
            totalApis += accountDTO.getStats().getApiCount();
        }
        
        partnerDTO.setAccounts(accountDTOs);
        partnerDTO.setStats(new NestedDataDTO.PartnerStats(
            accounts.size(),
            totalSolutions,
            totalEnvironments,
            totalApis
        ));
        
        return partnerDTO;
    }
    
    private NestedDataDTO.AccountDTO buildAccountDTO(Account account) {
        NestedDataDTO.AccountDTO accountDTO = new NestedDataDTO.AccountDTO(
            account.getAccountId().toString(),
            account.getName(),
            account.getDescription(),
            getAccountIcon(account.getName())
        );
        
        // Get solutions for this account
        List<Solution> solutions = solutionRepository.findByAccount(account);
        List<NestedDataDTO.SolutionDTO> solutionDTOs = new ArrayList<>();
        
        int totalEnvironments = 0;
        int totalApis = 0;
        
        for (Solution solution : solutions) {
            NestedDataDTO.SolutionDTO solutionDTO = buildSolutionDTO(solution);
            solutionDTOs.add(solutionDTO);
            
            // Aggregate stats
            totalEnvironments += solutionDTO.getStats().getEnvironmentCount();
            totalApis += solutionDTO.getStats().getApiCount();
        }
        
        accountDTO.setSolutions(solutionDTOs);
        accountDTO.setStats(new NestedDataDTO.AccountStats(
            solutions.size(),
            totalEnvironments,
            totalApis
        ));
        
        return accountDTO;
    }
    
    private NestedDataDTO.SolutionDTO buildSolutionDTO(Solution solution) {
        NestedDataDTO.SolutionDTO solutionDTO = new NestedDataDTO.SolutionDTO(
            solution.getSolutionId().toString(),
            solution.getName(),
            solution.getDescription(),
            NestedDataDTO.getSolutionIcon(solution.getName())
        );
        
        // Get environments for this solution
        Set<Environment> environments = solution.getEnvironments();
        List<NestedDataDTO.EnvironmentDTO> environmentDTOs = new ArrayList<>();
        
        int totalApis = 0;
        
        for (Environment environment : environments) {
            NestedDataDTO.EnvironmentDTO environmentDTO = buildEnvironmentDTO(environment, solution);
            environmentDTOs.add(environmentDTO);
            
            // Aggregate stats
            totalApis += environmentDTO.getApis().size();
        }
        
        solutionDTO.setEnvironments(environmentDTOs);
        solutionDTO.setStats(new NestedDataDTO.SolutionStats(
            environments.size(),
            totalApis
        ));
        
        return solutionDTO;
    }
    
    private NestedDataDTO.EnvironmentDTO buildEnvironmentDTO(Environment environment, Solution solution) {
        NestedDataDTO.EnvironmentDTO environmentDTO = new NestedDataDTO.EnvironmentDTO(
            environment.getId().toString(),
            environment.getName(),
            environment.getType().getDisplayName(),
            environment.getType().getCode(),
            environment.getType().getColor(),
            NestedDataDTO.getEnvironmentIcon(environment.getType())
        );
        
        // Get APIs for this environment and solution
        List<Api> apis = apiRepository.findByEnvironmentAndSolution(environment, solution);
        List<NestedDataDTO.ApiDTO> apiDTOs = apis.stream()
            .map(NestedDataDTO::fromApi)
            .collect(Collectors.toList());
        
        environmentDTO.setApis(apiDTOs);
        
        return environmentDTO;
    }
    
    private String getPartnerLogo(String partnerName) {
        String lowerName = partnerName.toLowerCase();
        if (lowerName.contains("microsoft")) {
            return "fab fa-microsoft";
        } else if (lowerName.contains("amazon") || lowerName.contains("aws")) {
            return "fab fa-aws";
        } else if (lowerName.contains("google")) {
            return "fab fa-google";
        } else if (lowerName.contains("apple")) {
            return "fab fa-apple";
        } else if (lowerName.contains("facebook") || lowerName.contains("meta")) {
            return "fab fa-facebook";
        } else {
            return "fas fa-building";
        }
    }
    
    private String getAccountIcon(String accountName) {
        String lowerName = accountName.toLowerCase();
        if (lowerName.contains("cloud") || lowerName.contains("azure") || lowerName.contains("aws")) {
            return "fas fa-cloud";
        } else if (lowerName.contains("enterprise")) {
            return "fas fa-building";
        } else if (lowerName.contains("development")) {
            return "fas fa-code";
        } else if (lowerName.contains("production")) {
            return "fas fa-industry";
        } else {
            return "fas fa-briefcase";
        }
    }
    
    public List<Environment> getAllEnvironments() {
        return environmentRepository.findAllActiveEnvironments();
    }
    
    public List<Api> getAllApis() {
        return apiRepository.findAllActiveApis();
    }
    
    public List<Api> getApisByEnvironment(Environment.EnvironmentType environmentType) {
        Environment environment = environmentRepository.findByType(environmentType).orElse(null);
        if (environment != null) {
            return apiRepository.findByEnvironmentAndActiveTrue(environment);
        }
        return new ArrayList<>();
    }
    
    public List<Api> getApisBySolution(UUID solutionId) {
        Solution solution = solutionRepository.findById(solutionId).orElse(null);
        if (solution != null) {
            return apiRepository.findBySolutionAndActiveTrue(solution);
        }
        return new ArrayList<>();
    }
}