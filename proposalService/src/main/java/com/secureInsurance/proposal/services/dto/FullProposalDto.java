package com.secureInsurance.proposal.services.dto;

import lombok.Data;

import java.util.List;

@Data
public class FullProposalDto {
    private ProposalDto proposalDto;
    private CustomerDto customerDto;
    private List<CoverDto> coverDto;
    private List<RiskDto> riskDto;
    private DepartmentDto departmentDto;
    private ProductDto productDto;
}
