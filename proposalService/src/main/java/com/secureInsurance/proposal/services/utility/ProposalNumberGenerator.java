package com.secureInsurance.proposal.services.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProposalNumberGenerator {
    private static String lastDate = "";
    private static int sequence = 0;

    public static String generateProposalNumber(){
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        if(!today.equals(lastDate)){
            lastDate = today;
            sequence = 1;
        }
        else {
            sequence++;
        }
        String formatedSeq= String.format("%04d",sequence);
        return today + formatedSeq;
    }
}
