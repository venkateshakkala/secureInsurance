package com.secureInsurance.proposal.services.service;

import com.secureInsurance.proposal.services.dto.CustomerMapProposalDto;
import com.secureInsurance.proposal.services.entity.CustomerMapProposal;

import java.util.Optional;

public interface ICustomerMapProposal {
     void mappingCustomerProposal(CustomerMapProposal customerMapProposal);
     Optional<CustomerMapProposalDto> fetchCustomerByProposalNumber(String proposalNumber);
}
