package com.example.service;

import com.example.dto.HierarchicalPartnerDTO;
import com.example.dto.HierarchicalAccountDTO;
import com.example.dto.HierarchicalSolutionDTO;
import com.example.entity.Partner;
import com.example.entity.Account;
import com.example.entity.Solution;
import com.example.entity.Environment;
import com.example.repository.PartnerRepository;
import com.example.repository.AccountRepository;
import com.example.repository.SolutionRepository;
import com.example.repository.EnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HierarchicalViewService {

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private EnvironmentRepository environmentRepository;

    public List<HierarchicalPartnerDTO> getHierarchicalData() {
        // Fetch all partners
        List<Partner> partners = partnerRepository.findAll();
        
        return partners.stream()
                .map(this::convertToHierarchicalPartnerDTO)
                .collect(Collectors.toList());
    }

    private HierarchicalPartnerDTO convertToHierarchicalPartnerDTO(Partner partner) {
        HierarchicalPartnerDTO partnerDTO = new HierarchicalPartnerDTO(
                partner.getPartnerId(),
                partner.getName(),
                partner.getDescription(),
                partner.getLogoUrl(),
                null // Partner doesn't have status
        );

        // Fetch accounts for this partner
        List<Account> accounts = accountRepository.findByPartner(partner);
        
        List<HierarchicalAccountDTO> accountDTOs = accounts.stream()
                .map(this::convertToHierarchicalAccountDTO)
                .collect(Collectors.toList());

        partnerDTO.setAccounts(accountDTOs);
        partnerDTO.setAccountCount(accountDTOs.size());
        
        // Calculate total solution count for this partner
        int totalSolutions = accountDTOs.stream()
                .mapToInt(HierarchicalAccountDTO::getSolutionCount)
                .sum();
        partnerDTO.setSolutionCount(totalSolutions);

        return partnerDTO;
    }

    private HierarchicalAccountDTO convertToHierarchicalAccountDTO(Account account) {
        HierarchicalAccountDTO accountDTO = new HierarchicalAccountDTO(
                account.getAccountId(),
                account.getName(),
                account.getDescription(),
                null // Account doesn't have status
        );

        // Get solutions for this account from the relationship
        List<HierarchicalSolutionDTO> solutionDTOs = account.getSolutions().stream()
                .map(this::convertToHierarchicalSolutionDTO)
                .collect(Collectors.toList());

        accountDTO.setSolutions(solutionDTOs);
        accountDTO.setSolutionCount(solutionDTOs.size());

        return accountDTO;
    }

    private HierarchicalSolutionDTO convertToHierarchicalSolutionDTO(Solution solution) {
        HierarchicalSolutionDTO solutionDTO = new HierarchicalSolutionDTO(
                solution.getSolutionId(),
                solution.getName(),
                solution.getDescription(),
                null // Solution doesn't have status
        );

        // Count environments for this solution from the new relationship
        solutionDTO.setEnvironmentCount(solution.getSolutionEnvironments().size());

        return solutionDTO;
    }
} 