package com.secureInsurance.proposal.services.mapper;

import com.secureInsurance.proposal.services.dto.CustomerDto;
import com.secureInsurance.proposal.services.dto.ProposalDto;
import com.secureInsurance.proposal.services.entity.ProposalCumPayments;
import org.springframework.stereotype.Component;

@Component
public class ProposalCumPaymentsMapper {
    public ProposalCumPayments mapToProposalCumPayments(ProposalDto proposalDto, CustomerDto customerDto){
        ProposalCumPayments proposalCumPayments = new ProposalCumPayments();
        proposalCumPayments.setCustomerId(customerDto.getCustomerId());
        proposalCumPayments.setCustomerName(customerDto.getFirstName()+ " " +customerDto.getLastName());
        proposalCumPayments.setMobileNumber(customerDto.getMobileNumber());
        proposalCumPayments.setNetPremium(proposalDto.getNetPremium());
        proposalCumPayments.setGst(proposalDto.getGst());
        proposalCumPayments.setTotalPremium(proposalDto.getTotalPremium());

        return proposalCumPayments;

    }
}
