package com.secureInsurance.proposal.services.dto;

import lombok.Data;

@Data
public class CoverDto {
    String coverCode;
    String coverName;
    long sumInsured;
    long premium;
}
