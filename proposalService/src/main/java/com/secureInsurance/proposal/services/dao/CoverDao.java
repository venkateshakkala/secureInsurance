package com.secureInsurance.proposal.services.dao;

import com.secureInsurance.proposal.services.dto.CoverDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CoverDao {
    JdbcTemplate jdbcTemplate;
    public void saveCover(String proposalNumber, CoverDto coverDto){
        String sql = "insert into covers_details (proposal_number, cover_code,cover_name,sum_insured,premium)" +
                     "values (?,?,?,?,?)";
        jdbcTemplate.update(sql,
                proposalNumber,
                coverDto.getCoverCode(),
                coverDto.getCoverName(),
                coverDto.getSumInsured(),
                coverDto.getPremium());
    }
    public List<CoverDto> getCoversByProposal(String proposalNumber) {
        String sql = "select * from covers_details where proposal_number = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CoverDto c = new CoverDto();
            c.setCoverCode(rs.getString("cover_code"));
            c.setCoverName(rs.getString("cover_name"));
            c.setSumInsured(rs.getLong("sum_insured"));
            c.setPremium(rs.getLong("premium"));
            return c;
        }, proposalNumber);
    }

}
