package com.secureInsurance.proposal.services.mapper;

import com.secureInsurance.proposal.services.dto.ProposalDto;
import com.secureInsurance.proposal.services.entity.ProposalDetails;
import org.springframework.stereotype.Component;

@Component
public class ProposalMapper {

    // ENTITY → DTO
    public ProposalDto mapToProposalDto(ProposalDetails proposalDetails) {

        ProposalDto proposalDto = new ProposalDto();

        //proposalDto.setProposalNumber(proposalDetails.getProposalNumber());
        proposalDto.setDepartmentCode(proposalDetails.getDepartmentCode());
        proposalDto.setProductCode(proposalDetails.getProductCode());
        proposalDto.setPolicyStartDate(proposalDetails.getPolicyStartDate());
        proposalDto.setPolicyEndDate(proposalDetails.getPolicyEndDate());
        proposalDto.setPolicyTenure(proposalDetails.getPolicyTenure());
        proposalDto.setRemarks(proposalDetails.getRemarks());
        proposalDto.setTotalSumInsured(proposalDetails.getTotalSumInsured());
        proposalDto.setNetPremium(proposalDetails.getNetPremium());
        proposalDto.setGst(proposalDetails.getGst());
        proposalDto.setTotalPremium(proposalDetails.getTotalPremium());

        return proposalDto;
    }

    // DTO → ENTITY
    public ProposalDetails mapToProposalDetails(ProposalDto dto) {

        ProposalDetails proposalDetails = new ProposalDetails();

        //proposalDetails.setProposalNumber(dto.getProposalNumber());
        proposalDetails.setDepartmentCode(dto.getDepartmentCode());
        proposalDetails.setProductCode(dto.getProductCode());
        proposalDetails.setPolicyStartDate(dto.getPolicyStartDate());
        proposalDetails.setPolicyEndDate(dto.getPolicyEndDate());
        proposalDetails.setPolicyTenure(dto.getPolicyTenure());
        proposalDetails.setRemarks(dto.getRemarks());
        proposalDetails.setTotalSumInsured(dto.getTotalSumInsured());
        proposalDetails.setNetPremium(dto.getNetPremium());
        proposalDetails.setGst(dto.getGst());
        proposalDetails.setTotalPremium(dto.getTotalPremium());

        return proposalDetails;
    }
}
