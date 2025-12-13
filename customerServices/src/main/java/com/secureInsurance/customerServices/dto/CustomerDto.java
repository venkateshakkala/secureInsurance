package com.secureInsurance.customerServices.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CustomerDto {
    private int customerId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String mobileNumber;
}
