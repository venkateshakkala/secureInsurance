package com.secureInsurance.proposal.services.feignClient;

import com.secureInsurance.proposal.services.dto.DepartmentDto;
import com.secureInsurance.proposal.services.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "dropDownClient", url = "http://localhost:8090/secureInsurance")
public interface DropDownFeignClient {

    @GetMapping("/department")
    List<DepartmentDto> getDepartments();

    @GetMapping("/product/{departmentCode}")
    List<ProductDto> getProductsByDepartment(@PathVariable String departmentCode);
}