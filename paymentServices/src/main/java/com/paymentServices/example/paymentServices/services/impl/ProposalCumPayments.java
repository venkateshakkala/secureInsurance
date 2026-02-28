package com.paymentServices.example.paymentServices.services.impl;

import com.paymentServices.example.paymentServices.dao.ProposalCumPaymentsDao;
import com.paymentServices.example.paymentServices.dto.ProposalCumPaymentsDto;
import com.paymentServices.example.paymentServices.services.IProposalCumPayments;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class ProposalCumPayments implements IProposalCumPayments {
    private final ProposalCumPaymentsDao proposalCumPaymentsDao;
    @Override
    public ProposalCumPaymentsDto populateProposalCumPaymentsByProposalNumber(String proposalNumber) {
        final ProposalCumPaymentsDto proposalCumPaymentsDto;
        try{
            proposalCumPaymentsDto =  proposalCumPaymentsDao.populateProposalCumPayments(proposalNumber);
        }
        catch (Exception e){
            return null;
        }
        if(proposalCumPaymentsDto!=null){
            return proposalCumPaymentsDto;
        }
            log.error("no data found");
        return null;
    }

    @Override
    public List<ProposalCumPaymentsDto> populateProposalCumPaymentsByMobileNumber(String mobileNumber) {
        final List<ProposalCumPaymentsDto> proposalCumPaymentsDto;
        List<ProposalCumPaymentsDto> resultList;
        try{
             resultList= proposalCumPaymentsDao.populateProposalCumPaymentsByMobileNumber(mobileNumber);
        }
        catch (Exception e){
            return null;
        }
        if(resultList!=null){
            return resultList;
        }
       log.error("no data found in services error by mobile number ");
        return null;
    }

    public List<Map<String,String>> populateProposalsByMobileNumber(String mobileNumber){
        final List<Map<String,String>> result;
        try{
            result = proposalCumPaymentsDao.populateProposalNumbers(mobileNumber);
        }
        catch (Exception e){
            log.error("not found in services");
            return null;
        }
        if(result!=null){
            return result;
        }
        return null;
    }
}
