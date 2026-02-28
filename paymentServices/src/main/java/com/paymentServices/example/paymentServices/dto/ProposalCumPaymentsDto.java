package com.paymentServices.example.paymentServices.dto;

import lombok.Data;

@Data
public class ProposalCumPaymentsDto {
    private String proposalNumber;
    private long customerId;
    private String customerName;
    private String mobileNumber;
    private double netPremium;
    private double gst;
    private double totalPremium;
    private String status;
}
