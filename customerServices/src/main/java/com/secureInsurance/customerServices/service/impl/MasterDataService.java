package com.secureInsurance.customerServices.service.impl;

import com.secureInsurance.customerServices.dao.DepartmentDao;
import com.secureInsurance.customerServices.dao.ProductDao;
import com.secureInsurance.customerServices.model.Department;
import com.secureInsurance.customerServices.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MasterDataService {
    private DepartmentDao departmentDao;
    private ProductDao productDao;

    public List<Department> getAllDepartments(){
        return departmentDao.getAllDepartments();
    }
    public List<Product> getALlProducts(String DepartmentCode){
        return productDao.getAllProducts(DepartmentCode);
    }
}
