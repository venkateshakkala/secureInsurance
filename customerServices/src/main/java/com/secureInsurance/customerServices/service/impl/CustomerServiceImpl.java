package com.secureInsurance.customerServices.service.impl;

import com.secureInsurance.customerServices.dto.CustomerDto;
import com.secureInsurance.customerServices.entity.Customer;
import com.secureInsurance.customerServices.exception.CustomerAlreadyExitsException;
import com.secureInsurance.customerServices.exception.ResourceNotFoundException;
import com.secureInsurance.customerServices.mapper.CustomerMapper;
import com.secureInsurance.customerServices.repository.CustomerRepository;
import com.secureInsurance.customerServices.service.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public void crateCustomer(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer= customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExitsException("customer is already present with this mobile number" + customerDto.getMobileNumber());
        }
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setAddress(customerDto.getAddress());
        customer.setMobileNumber(customerDto.getMobileNumber());
        customer.setEmail(customerDto.getEmail());

        customerRepository.save(customer);
    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        Customer customer= customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("No data found with this mobile number "+ mobileNumber)
        );

        return CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
    }

    @Override
    public Customer updateCustomer(CustomerDto customerDto) {
        Customer customer= customerRepository.findByMobileNumber(customerDto.getMobileNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("No data found with this mobile number "+ customerDto.getMobileNumber())
        );
        CustomerMapper.mapToCustomer(customerDto,customer);

       return customerRepository.save(customer);

    }

    @Override
    @Transactional
    public void deleteCustomer(String mobileNumber) {
        Customer customer= customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("No data found with this mobile number "+mobileNumber)
        );

        customerRepository.deleteByMobileNumber(mobileNumber);

    }
}
