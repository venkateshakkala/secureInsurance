package com.secureInsurance.customerServices.mapper;

import com.secureInsurance.customerServices.dto.CustomerDto;
import com.secureInsurance.customerServices.entity.Customer;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setCustomerId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setMobileNumber(customer.getMobileNumber());
        customerDto.setEmail(customer.getEmail());

        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer){
        //customer.setId(customerDto.getId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setAddress(customerDto.getAddress());
        customer.setMobileNumber(customerDto.getMobileNumber());
        customer.setEmail(customerDto.getEmail());

        return customer;
    }
}
