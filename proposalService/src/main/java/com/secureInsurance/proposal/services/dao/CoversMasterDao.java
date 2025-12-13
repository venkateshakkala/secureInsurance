package com.secureInsurance.proposal.services.dao;

import com.secureInsurance.proposal.services.model.CoversMaster;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CoversMasterDao {
    private JdbcTemplate jdbcTemplate;

    public List<CoversMaster> getAllCovers(){
        String sql = "select cover_code,cover_name from covers_master";
        return jdbcTemplate.query(sql,(rs,rowNum)-> {
            CoversMaster c = new CoversMaster();
            c.setCoverCode(rs.getString("cover_code"));
            c.setCoverName(rs.getString("cover_name"));
            return c;
        });
    }
}
