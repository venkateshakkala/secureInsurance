package com.secureInsurance.proposal.services.repository;

import com.secureInsurance.proposal.services.dto.ProposalDto;
import com.secureInsurance.proposal.services.entity.ProposalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProposalRepository extends JpaRepository<ProposalDetails,Long> {
    ProposalDetails findByProposalNumber(String proposalNumber);

    Optional<ProposalDetails> findByRequestId(String requestId);
}

