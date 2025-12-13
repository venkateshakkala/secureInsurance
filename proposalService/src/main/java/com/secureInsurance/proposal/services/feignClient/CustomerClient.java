package com.secureInsurance.proposal.services.feignClient;

import com.secureInsurance.proposal.services.dto.CustomerDto;
import com.secureInsurance.proposal.services.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "customerClient", url = "http://localhost:8090/secureInsurance")
public interface CustomerClient {
    // Fetch customer
    @GetMapping("/fetch")
    public CustomerDto getCustomerByMobileNumber(@RequestParam("mobileNumber") String mobileNumber);

}
