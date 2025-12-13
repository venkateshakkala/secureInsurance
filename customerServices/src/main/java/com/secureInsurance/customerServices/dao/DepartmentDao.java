package com.secureInsurance.customerServices.dao;

import com.secureInsurance.customerServices.model.Department;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
@AllArgsConstructor
public class DepartmentDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Department> getAllDepartments(){
        String sql = "select department_code,department_name from department_master";

        return jdbcTemplate.query(sql,(rs, rowNum) -> {
            Department d= new Department();
            d.setDepartmentCode(rs.getString("department_code"));
            d.setDepartmentName(rs.getString("department_name"));
            return d;
        });

    }
}
