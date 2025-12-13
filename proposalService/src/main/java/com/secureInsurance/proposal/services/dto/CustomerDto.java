package com.secureInsurance.proposal.services.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private long customerId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
}
