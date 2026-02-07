package com.secureInsurance.proposal.services.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.xml.transform.sax.SAXResult;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class ProposalDto {

    //private String proposalNumber;

    private String mobileNumber;

    private String departmentCode;

    private String productCode;

    private long totalSumInsured;
    private long netPremium;
    private long gst;
    private long totalPremium;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate policyStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate policyEndDate;

    private int policyTenure;
    private String remarks;

    private List<RiskDto> risks;
    private List<CoverDto> covers;
}
