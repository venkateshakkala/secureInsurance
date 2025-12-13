package com.secureInsurance.proposal.services.service.impl;

import com.secureInsurance.proposal.services.dao.CoverDao;
import com.secureInsurance.proposal.services.dao.RiskDao;
import com.secureInsurance.proposal.services.dto.*;
import com.secureInsurance.proposal.services.entity.ProposalDetails;
import com.secureInsurance.proposal.services.feignClient.CustomerClient;
import com.secureInsurance.proposal.services.feignClient.DropDownFeignClient;
import com.secureInsurance.proposal.services.mapper.ProposalMapper;
import com.secureInsurance.proposal.services.repository.ProposalRepository;
import com.secureInsurance.proposal.services.service.IProposalService;
import com.secureInsurance.proposal.services.utility.ProposalNumberGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProposalImpl implements IProposalService {

    private final ProposalRepository proposalRepository;
    private final RiskDao riskDao;
    private final CoverDao coverDao;
    private final CustomerClient customerClient;
    private final DropDownFeignClient dropDownFeignClient;
    private final ProposalMapper proposalMapper;

    @Override
    public void createProposal(ProposalDto proposalDto) {

        ProposalDetails proposal = new ProposalDetails();
        System.out.println("MOBILE FROM UI = " + proposalDto.getMobileNumber());
        CustomerDto customerDto = customerClient.getCustomerByMobileNumber(proposalDto.getMobileNumber());
        System.out.println("MOBILE FROM UI = " + proposalDto.getMobileNumber());
        //Department Dropdown
        List<DepartmentDto> departmentDtoList = dropDownFeignClient.getDepartments();
        DepartmentDto departmentDto = departmentDtoList.stream()
                .filter(d -> d.getDepartmentCode().equals(proposalDto.getDepartmentCode()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Department Code not found"));
        proposal.setDepartmentCode(departmentDto.getDepartmentCode());
        proposal.setDepartmentName(departmentDto.getDepartmentName());

        //ProductDropdown
        List<ProductDto> productDtoList = dropDownFeignClient.getProductsByDepartment(proposalDto.getDepartmentCode());
        ProductDto productDto = productDtoList.stream()
                .filter((p -> p.getProductCode().equals(proposalDto.getProductCode())))
                        .findFirst()
                .orElseThrow(()-> new RuntimeException("Product code not found"));

        proposal.setProductCode(productDto.getProductCode());
        proposal.setProductName(productDto.getProductName());
        System.out.println(productDto.getProductCode());

        //Customer data
        proposal.setCustomerId(customerDto.getCustomerId());
        proposal.setCustomerName(customerDto.getFirstName() + " " + customerDto.getLastName());



        String proposalNumber = ProposalNumberGenerator.generateProposalNumber();
        proposal.setProposalNumber(proposalNumber);
        proposal.setDepartmentCode(proposalDto.getDepartmentCode());

        proposal.setProductCode(proposalDto.getProductCode());

        proposal.setPolicyStartDate(proposalDto.getPolicyStartDate());
        proposal.setPolicyEndDate(proposalDto.getPolicyEndDate());
        proposal.setPolicyTenure(proposalDto.getPolicyTenure());
        proposal.setRemarks(proposalDto.getRemarks());

        proposal.setTotalSumInsured(proposalDto.getTotalSumInsured());
        proposal.setNetPremium(proposalDto.getNetPremium());
        proposal.setGst(proposalDto.getGst());
        proposal.setTotalPremium(proposalDto.getTotalPremium());

        proposalRepository.save(proposal);

        if (proposalDto.getRisks() != null) {
            proposalDto.getRisks().forEach(r -> riskDao.saveRiskDetails(proposalNumber, r));
        }

        if (proposalDto.getCovers() != null) {
            proposalDto.getCovers().forEach(c -> coverDao.saveCover(proposalNumber, c));
        }
    }

    @Override
    public FullProposalDto fetchProposal(String proposalNumber) {

        ProposalDetails proposalDetails = proposalRepository.findByProposalNumber(proposalNumber);

        if (proposalDetails == null) {
            throw new RuntimeException("Proposal Not Found: " + proposalNumber);
        }

        ProposalDto proposalDto = proposalMapper.mapToProposalDto(proposalDetails);

        //CustomerDto customerDto = customerClient.getCustomerByMobileNumber(proposalDto.getCustomerName());

        List<RiskDto> risks = riskDao.getRisksByProposal(proposalNumber);

        List<CoverDto> covers = coverDao.getCoversByProposal(proposalNumber);

        FullProposalDto full = new FullProposalDto();
        full.setProposalDto(proposalDto);
        //full.setCustomerDto(customerDto);
        full.setRiskDto(risks);
        full.setCoverDto(covers);

        return full;
    }
}
