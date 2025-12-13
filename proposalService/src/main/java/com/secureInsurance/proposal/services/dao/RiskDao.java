package com.secureInsurance.proposal.services.dao;

import com.secureInsurance.proposal.services.dto.RiskDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@Data
public class RiskDao {
    private final JdbcTemplate jdbcTemplate;

    public void saveRiskDetails(String proposalNumber, RiskDto riskDto){
        String sql = "insert into risks_details (proposal_number,insured_name,age,gender,relation,occupation) " +
                      "values (?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                proposalNumber,
                riskDto.getInsuredName(),
                riskDto.getAge(),
                riskDto.getGender(),
                riskDto.getRelation(),
                riskDto.getOccupation());
    }
    public List<RiskDto> getRisksByProposal(String proposalNumber) {
        String sql = "select * from risks_details where proposal_number = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RiskDto r = new RiskDto();
            r.setInsuredName(rs.getString("insured_name"));
            r.setAge(rs.getInt("age"));
            r.setGender(rs.getString("gender"));
            r.setRelation(rs.getString("relation"));
            r.setOccupation(rs.getString("occupation"));
            return r;
        }, proposalNumber);
    }

}
