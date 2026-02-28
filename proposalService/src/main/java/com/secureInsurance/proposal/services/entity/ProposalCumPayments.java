package com.secureInsurance.proposal.services.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "proposal_cum_payments")
@AllArgsConstructor
@NoArgsConstructor
public class ProposalCumPayments {

    @Id
    @Column(name = "proposal_number")
    private String proposalNumber;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_mobile_number")
    private String mobileNumber;

    @Column(name = "net_premium")
    private double netPremium;

    @Column (name = "gst")
    private double gst;

    @Column (name = "total_premium")
    private double totalPremium;

    @Column(name = "status")
    private String status;
}
