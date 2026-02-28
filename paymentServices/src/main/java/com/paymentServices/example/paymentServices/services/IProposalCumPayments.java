package com.paymentServices.example.paymentServices.services;

import com.paymentServices.example.paymentServices.dto.ProposalCumPaymentsDto;

import java.util.List;
import java.util.Map;

public interface IProposalCumPayments {
    public ProposalCumPaymentsDto populateProposalCumPaymentsByProposalNumber(String proposalNumber);
    public List<ProposalCumPaymentsDto> populateProposalCumPaymentsByMobileNumber(String mobileNumber);
    public List<Map<String,String>> populateProposalsByMobileNumber(String mobileNumber);
}
