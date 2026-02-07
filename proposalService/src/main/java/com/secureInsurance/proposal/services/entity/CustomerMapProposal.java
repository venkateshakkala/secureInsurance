package com.secureInsurance.proposal.services.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_map_proposal")
public class CustomerMapProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_id")
    private int mapId;
    @Column(name = "customer_id")
    private long customerId;
    @Column(name ="proposalNumber")
    private String proposalNumber;
    @Column(name = "mobileNumber")
    private String mobileNumber;
}
