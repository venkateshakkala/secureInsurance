package com.secureInsurance.proposal.services.controller;

import com.secureInsurance.proposal.services.dto.FullProposalDto;
import com.secureInsurance.proposal.services.dto.ProposalDto;
import com.secureInsurance.proposal.services.dto.ResponseDto;
import com.secureInsurance.proposal.services.service.IProposalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proposal")
@AllArgsConstructor
public class ProposalController {
    IProposalService iProposalService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> createProposal(
            @RequestBody ProposalDto proposalDto) {

        if (proposalDto.getMobileNumber() == null ||
                proposalDto.getMobileNumber().isBlank()) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto("400", "Mobile number is required"));
        }

        String proposalNumber = iProposalService.createProposal(proposalDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Proposal created successfully "+ proposalNumber));
    }


    @GetMapping("/fetch/{proposalNumber}")
    public ResponseEntity<FullProposalDto> fetchProposal(@PathVariable String proposalNumber){
        FullProposalDto fullProposalDto = iProposalService.fetchProposal(proposalNumber);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(fullProposalDto);
    }
}
