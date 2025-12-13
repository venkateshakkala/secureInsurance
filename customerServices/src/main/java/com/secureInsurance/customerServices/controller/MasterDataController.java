package com.secureInsurance.customerServices.controller;

import com.secureInsurance.customerServices.model.Department;
import com.secureInsurance.customerServices.model.Product;
import com.secureInsurance.customerServices.service.impl.MasterDataService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secureInsurance")
@CrossOrigin("*")
@AllArgsConstructor
public class MasterDataController {
    private MasterDataService masterDataService;
    @GetMapping("/department")
    public List<Department> getDepartments(){
        return masterDataService.getAllDepartments();
    }
    @GetMapping("/product/{departmentCode}")
    public List<Product> getProducts(@PathVariable("departmentCode") String departmentCode){
        return masterDataService.getALlProducts(departmentCode);
    }
}
