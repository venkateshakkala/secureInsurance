package com.secureInsurance.proposal.services.controller;

import com.secureInsurance.proposal.services.coversServices.CoversService;
import com.secureInsurance.proposal.services.model.CoversMaster;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8090")
@RestController
@AllArgsConstructor
@RequestMapping("/proposal")
public class CoversController {
    @Autowired
    private CoversService coversService;
    @GetMapping("/covers")
    public List<CoversMaster> getCovers(){
        return coversService.getAllCovers();
    }
}
