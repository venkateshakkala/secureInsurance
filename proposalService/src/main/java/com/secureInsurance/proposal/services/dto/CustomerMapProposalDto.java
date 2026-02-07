package com.secureInsurance.proposal.services.dto;

import lombok.Data;

@Data
public class CustomerMapProposalDto {
    private long customerId;
    private String mobileNumber;
    private String proposalNumber;
}
