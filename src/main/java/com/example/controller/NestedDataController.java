package com.example.controller;

import com.example.dto.NestedDataDTO;
import com.example.entity.Environment;
import com.example.service.NestedDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/nested-data")
public class NestedDataController {
    
    @Autowired
    private NestedDataService nestedDataService;
    
    @GetMapping
    public String showNestedDataPage(Model model) {
        return "nested-data";
    }
    
    @GetMapping("/api/structure")
    @ResponseBody
    public List<NestedDataDTO.PartnerDTO> getNestedDataStructure() {
        return nestedDataService.getNestedDataStructure();
    }
    
    @GetMapping("/api/environments")
    @ResponseBody
    public List<Environment> getAllEnvironments() {
        return nestedDataService.getAllEnvironments();
    }
}