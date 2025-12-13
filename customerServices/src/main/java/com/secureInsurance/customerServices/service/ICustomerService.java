package com.secureInsurance.customerServices.service;

import com.secureInsurance.customerServices.dto.CustomerDto;
import com.secureInsurance.customerServices.entity.Customer;

public interface ICustomerService {
    void crateCustomer(CustomerDto customerDto);
    CustomerDto fetchCustomer(String mobileNumber);
    Customer updateCustomer(CustomerDto customerDto);
    void deleteCustomer(String mobileNumber);
}
