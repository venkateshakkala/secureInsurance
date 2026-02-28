package com.secureInsurance.customerServices.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private int customerId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String mobileNumber;
}
