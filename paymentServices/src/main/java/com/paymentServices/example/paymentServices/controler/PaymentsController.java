package com.paymentServices.example.paymentServices.controler;

import com.paymentServices.example.paymentServices.dto.ProposalCumPaymentsDto;
import com.paymentServices.example.paymentServices.dto.ResponseDto;
import com.paymentServices.example.paymentServices.services.IProposalCumPayments;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentsController {
    private final IProposalCumPayments iProposalCumPayments;
    @GetMapping("/search/{proposalNumber}")
    public ResponseEntity<ProposalCumPaymentsDto> getProposalsData(@PathVariable String proposalNumber){
       ProposalCumPaymentsDto proposalCumPaymentsDto= iProposalCumPayments.populateProposalCumPaymentsByProposalNumber(proposalNumber);
       return ResponseEntity.status(HttpStatus.OK).body(proposalCumPaymentsDto);
    }

    @GetMapping("/searchAll")
    public ResponseEntity<List<ProposalCumPaymentsDto>> getProposalDataByMobileNumber(@RequestParam String mobileNumber) {
        List<ProposalCumPaymentsDto> resultList = iProposalCumPayments.populateProposalCumPaymentsByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Map<String,String>>> getProposalByMobileNumber(@RequestParam String mobileNumber){
        List<Map<String,String>> result = iProposalCumPayments.populateProposalsByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
