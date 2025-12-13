package com.secureInsurance.proposal.services.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class ProposalDto {

      //private long customerId;
//    private String customerName;
    private String mobileNumber;

    private String departmentCode;
    //private String departmentName;

    private String productCode;
    //private String productName;

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
