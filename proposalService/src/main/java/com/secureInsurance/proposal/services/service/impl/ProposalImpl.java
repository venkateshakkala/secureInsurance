package com.secureInsurance.proposal.services.service.impl;

import com.secureInsurance.proposal.services.Exception.CustomerServicesException;
import com.secureInsurance.proposal.services.dao.CoverDao;
import com.secureInsurance.proposal.services.dao.RiskDao;
import com.secureInsurance.proposal.services.dto.*;
import com.secureInsurance.proposal.services.entity.CustomerMapProposal;
import com.secureInsurance.proposal.services.entity.ProposalDetails;
import com.secureInsurance.proposal.services.feignClient.CustomerClient;
import com.secureInsurance.proposal.services.feignClient.DropDownFeignClient;
import com.secureInsurance.proposal.services.mapper.ProposalMapper;
import com.secureInsurance.proposal.services.repository.CustomerMapProposalRepository;
import com.secureInsurance.proposal.services.repository.ProposalRepository;
import com.secureInsurance.proposal.services.service.IProposalService;
import com.secureInsurance.proposal.services.utility.ProposalNumberGenerator;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ProposalImpl implements IProposalService {

    private final ProposalRepository proposalRepository;
    private final CustomerMapProposalRepository customerMapProposalRepository;
    private final RiskDao riskDao;
    private final CoverDao coverDao;
    private final CustomerClient customerClient;
    private final DropDownFeignClient dropDownFeignClient;
    private final ProposalMapper proposalMapper;
    private JdbcTemplate jdbcTemplate;
    @Override
    @Transactional
    public String createProposal(ProposalDto proposalDto) {
        try {
                Optional<ProposalDetails> existing = proposalRepository.findByRequestId(proposalDto.getRequestId());
                if (existing.isPresent()) {
                    return existing.get().getProposalNumber();
                }

            ProposalDetails proposal = new ProposalDetails();


            CustomerDto customerDto = new CustomerDto();
            try {
                customerDto = customerClient.getCustomerByMobileNumber(proposalDto.getMobileNumber());
            } catch (FeignException.FeignClientException e) {
                log.error("Customer services is down ");
                throw new CustomerServicesException("Error while calling Customer services on Proposal Services");
            } catch (Exception e) {
                throw new RuntimeException("something went wrong calling Customer Services");
            }
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
                    .orElseThrow(() -> new RuntimeException("Product code not found"));

            proposal.setProductCode(productDto.getProductCode());
            proposal.setProductName(productDto.getProductName());


            //Customer data
            proposal.setCustomerId(customerDto.getCustomerId());
            proposal.setCustomerName(customerDto.getFirstName() + " " + customerDto.getLastName());


            ProposalNumberGenerator proposalNumberGenerator = new ProposalNumberGenerator(jdbcTemplate);

            String proposalNumber = proposalNumberGenerator.generateProposalNumber();
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

            proposal.setRequestId(proposalDto.getRequestId());

            //mapping customer and proposal
            CustomerMapProposal customerMapProposal = new CustomerMapProposal();
            customerMapProposal.setProposalNumber(proposalNumber);
            customerMapProposal.setMobileNumber(customerDto.getMobileNumber());
            customerMapProposalRepository.save(customerMapProposal);

            proposalRepository.save(proposal);

            if (proposalDto.getRisks() != null) {
                proposalDto.getRisks().forEach(r -> riskDao.saveRiskDetails(proposalNumber, r));
            }

            if (proposalDto.getCovers() != null) {
                proposalDto.getCovers().forEach(c -> coverDao.saveCover(proposalNumber, c));
            }
            return proposalNumber;

    }catch (Exception e){
            return "Error while saving proposal" + e.getMessage();
        }

    }

    @Override
    public FullProposalDto fetchProposal(String proposalNumber) {

        ProposalDetails proposalDetails = proposalRepository.findByProposalNumber(proposalNumber);

        if (proposalDetails == null) {
            throw new RuntimeException("Proposal Not Found: " + proposalNumber);
        }

        ProposalDto proposalDto = proposalMapper.mapToProposalDto(proposalDetails);

        List<RiskDto> risks = riskDao.getRisksByProposal(proposalNumber);

        List<CoverDto> covers = coverDao.getCoversByProposal(proposalNumber);
        //Map ProposalData
        CustomerMapProposal customerMapProposal = customerMapProposalRepository
                .findByProposalNumber(proposalNumber)
                .orElseThrow(() -> new RuntimeException("Customer proposal map not found " + proposalNumber));
        String mobileNumber = customerMapProposal.getMobileNumber();
        CustomerDto customerDto = customerClient.getCustomerByMobileNumber(mobileNumber);

        // Fetch Department
        DepartmentDto departmentDto = dropDownFeignClient.getDepartments()
                .stream()
                .filter(d -> d.getDepartmentCode().equals(proposalDetails.getDepartmentCode()))
                .findFirst()
                .orElse(null);

        // Fetch Product
        ProductDto productDto = dropDownFeignClient
                .getProductsByDepartment(proposalDetails.getDepartmentCode())
                .stream()
                .filter(p -> p.getProductCode().equals(proposalDetails.getProductCode()))
                .findFirst()
                .orElse(null);

        FullProposalDto fullProposalDto = new FullProposalDto();
        fullProposalDto.setProposalDto(proposalDto);
        fullProposalDto.setCustomerDto(customerDto);
        fullProposalDto.setRiskDto(risks);
        fullProposalDto.setCoverDto(covers);
        fullProposalDto.setDepartmentDto(departmentDto);
        fullProposalDto.setProductDto(productDto);



        return fullProposalDto;
    }
}
