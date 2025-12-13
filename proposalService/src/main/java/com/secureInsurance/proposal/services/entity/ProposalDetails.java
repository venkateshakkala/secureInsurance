package com.secureInsurance.proposal.services.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "proposal_details")
public class ProposalDetails {
    @Id
    @Column(name = "proposal_number")
    String proposalNumber;

    @Column(name = "customer_id")
    long customerId;

    @Column(name = "customer_name")
    String customerName;

    @Column(name = "total_sum_insured")
    long totalSumInsured;

    @Column(name = "net_premium")
    long netPremium;

    @Column(name = "gst")
    long gst;

    @Column(name = "total_premium")
    long totalPremium;

    @Column(name = "department_code")
    String departmentCode;

    @Column(name = "department_name")
    String departmentName;

    @Column(name = "product_name")
    String productName;

    @Column(name = "product_code")
    String productCode;

    @Column(name ="policy_start_date")
    LocalDate policyStartDate;

    @Column(name = "policy_end_date")
    LocalDate policyEndDate;

    @Column(name = "policy_tenure")
    int policyTenure;

    @Column(name = "remarks")
    String remarks;
}
