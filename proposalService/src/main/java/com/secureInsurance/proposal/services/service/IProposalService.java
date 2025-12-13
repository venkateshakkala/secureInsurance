package com.secureInsurance.proposal.services.service;

import com.secureInsurance.proposal.services.dto.FullProposalDto;
import com.secureInsurance.proposal.services.dto.ProposalDto;

public interface IProposalService {
    void createProposal(ProposalDto proposalDto);
    FullProposalDto fetchProposal(String proposalNumber);

}
