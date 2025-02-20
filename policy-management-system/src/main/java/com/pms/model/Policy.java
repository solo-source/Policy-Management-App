package com.pms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String policyId;

    private LocalDate startDate;
    private Double totalPremiumAmount;
    private Double maturityAmount;
    private Integer numberOfYears;
    private String policyStatus;
    private String annuityTerm;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Getter and Setter for id
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for policyId
    public String getPolicyId() {
        return policyId;
    }
    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    // Getter and Setter for startDate
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    // Getter and Setter for totalPremiumAmount
    public Double getTotalPremiumAmount() {
        return totalPremiumAmount;
    }
    public void setTotalPremiumAmount(Double totalPremiumAmount) {
        this.totalPremiumAmount = totalPremiumAmount;
    }

    // Getter and Setter for maturityAmount
    public Double getMaturityAmount() {
        return maturityAmount;
    }
    public void setMaturityAmount(Double maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    // Getter and Setter for numberOfYears
    public Integer getNumberOfYears() {
        return numberOfYears;
    }
    public void setNumberOfYears(Integer numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    // Getter and Setter for policyStatus
    public String getPolicyStatus() {
        return policyStatus;
    }
    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    // Getter and Setter for annuityTerm
    public String getAnnuityTerm() {
        return annuityTerm;
    }
    public void setAnnuityTerm(String annuityTerm) {
        this.annuityTerm = annuityTerm;
    }

    // Getter and Setter for customer
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
