package com.paymentServices.example.paymentServices.dao;

import com.paymentServices.example.paymentServices.dto.ProposalCumPaymentsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@AllArgsConstructor
public class ProposalCumPaymentsDao {
    private final JdbcTemplate jdbcTemplate;
        public ProposalCumPaymentsDto populateProposalCumPayments(String proposalNumber) {
            String sql = "select * from proposal_cum_payments where proposal_number = ?";
            try {
                return jdbcTemplate.queryForObject(sql, new Object[]{proposalNumber}, (rs, rowNum) -> {
                    ProposalCumPaymentsDto p = new ProposalCumPaymentsDto();
                    p.setProposalNumber(rs.getString("proposal_number"));
                    p.setCustomerId(rs.getLong("customer_id"));
                    p.setCustomerName(rs.getString("customer_name"));
                    p.setMobileNumber(rs.getString("customer_mobile_number"));
                    p.setNetPremium(rs.getDouble("net_premium"));
                    p.setGst(rs.getDouble("gst"));
                    p.setTotalPremium(rs.getDouble("total_premium"));
                    p.setStatus(rs.getString("status"));

                    log.info("data found" + p.getMobileNumber());
                    return p;
                });
            }
            catch (EmptyResultDataAccessException e){
                log.info(proposalNumber);
                log.error("data not found in data base");
                return null;
            }
        }
        public List<ProposalCumPaymentsDto> populateProposalCumPaymentsByMobileNumber(String mobileNumber){
            String sql = "select * from proposal_cum_payments where customer_mobile_number = ?";
            try{
                return jdbcTemplate.query(sql,new Object[]{mobileNumber},(rs,rowNum)->{
                        ProposalCumPaymentsDto p = new ProposalCumPaymentsDto();
                p.setProposalNumber(rs.getString("proposal_number"));
                p.setCustomerId(rs.getLong("customer_id"));
                p.setCustomerName(rs.getString("customer_name"));
                p.setMobileNumber(rs.getString("customer_mobile_number"));
                p.setNetPremium(rs.getDouble("net_premium"));
                p.setGst(rs.getDouble("gst"));
                p.setTotalPremium(rs.getDouble("total_premium"));
                p.setStatus(rs.getString("status"));;

                log.info(mobileNumber);
                log.error("data found by mobileNumber");
                return p;
                });
        }
            catch (EmptyResultDataAccessException e){
                log.info(mobileNumber);
                log.error("no data found by mobileNumber");
                return null;
        }
}
    public List<Map<String,String>> populateProposalNumbers(String mobileNumber){
            String sql = "select proposal_number,status from proposal_cum_payments where customer_mobile_number = ?";
            try{
                return jdbcTemplate.query(sql,new Object[]{mobileNumber},(rs, rowNum) -> {
                    Map<String,String > result = new HashMap<>();
                    result.put("proposalNumber",rs.getString("proposal_number"));
                    result.put("status", rs.getString("status"));
                    log.info(mobileNumber);
                    return result;

                });
            }
            catch (EmptyResultDataAccessException e){
                log.error("not found in dao");
                return null;
            }
    }
}
