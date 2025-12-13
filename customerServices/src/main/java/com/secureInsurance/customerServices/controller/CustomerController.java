package com.secureInsurance.customerServices.controller;

import com.secureInsurance.customerServices.dto.CustomerDto;
import com.secureInsurance.customerServices.dto.ResponseDto;
import com.secureInsurance.customerServices.entity.Customer;
import com.secureInsurance.customerServices.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/secureInsurance")
public class CustomerController {
    private ICustomerService iCustomerService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCustomer(@RequestBody CustomerDto customerDto){
        iCustomerService.crateCustomer(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201","Customer Created successfully"));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomer(@RequestParam String mobileNumber){
         CustomerDto customerDto= iCustomerService.fetchCustomer(mobileNumber);
         return ResponseEntity.status(HttpStatus.OK).body(customerDto);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCustomer(@RequestBody CustomerDto customerDto){
        Customer customer= iCustomerService.updateCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto("200","Data updated successfully"));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCustomer(@RequestParam String mobileNumber){
         iCustomerService.deleteCustomer(mobileNumber);
         return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("200","Customer deleted successfully with the mobile number"));
    }


}
