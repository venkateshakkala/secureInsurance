package com.secureInsurance.proposal.services.service;

import com.secureInsurance.proposal.services.dto.FullProposalDto;
import com.secureInsurance.proposal.services.dto.ProposalDto;

public interface IProposalService {
    String createProposal(ProposalDto proposalDto);
    FullProposalDto fetchProposal(String proposalNumber);

}
