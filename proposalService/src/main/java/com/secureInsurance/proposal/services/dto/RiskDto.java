package com.secureInsurance.proposal.services.dto;

import lombok.Data;

@Data
public class RiskDto {
    String insuredName;
    int age;
    String gender;
    String relation;
    String occupation;
}
