package com.secureInsurance.proposal.services.utility;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Repository
@AllArgsConstructor
public class ProposalNumberGenerator{
     private JdbcTemplate  jdbcTemplate;

     public String generateProposalNumber(){

             SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withFunctionName("generate_proposal_number");
             return jdbcCall.executeFunction(String.class);

     }
}
