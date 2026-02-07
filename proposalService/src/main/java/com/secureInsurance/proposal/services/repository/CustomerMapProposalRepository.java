package com.secureInsurance.proposal.services.repository;

import com.secureInsurance.proposal.services.dto.CustomerMapProposalDto;
import com.secureInsurance.proposal.services.entity.CustomerMapProposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerMapProposalRepository extends JpaRepository<CustomerMapProposal,Long> {
    Optional<CustomerMapProposal> findByProposalNumber(String proposalNumber);
}
