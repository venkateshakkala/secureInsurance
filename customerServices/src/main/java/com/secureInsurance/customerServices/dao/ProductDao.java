package com.secureInsurance.customerServices.dao;

import com.secureInsurance.customerServices.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Product> getAllProducts(String departmentCode){
        String sql = "select product_code,product_name,department_code" +
                     " from product_master where department_code = ?";
        return jdbcTemplate.query(sql,new Object[]{departmentCode},(rs,rownum)->{
            Product p = new Product();
            p.setProductCode(rs.getString("product_code"));
            p.setProductName(rs.getString("product_name"));
            p.setDepartmentCode(rs.getString("department_code"));
            return p;
        });
    }
}

