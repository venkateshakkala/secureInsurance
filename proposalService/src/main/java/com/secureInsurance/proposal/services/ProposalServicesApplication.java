package com.secureInsurance.proposal.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProposalServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProposalServicesApplication.class, args);
	}

}
