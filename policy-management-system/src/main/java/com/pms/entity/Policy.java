package com.pms.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String policyId; // This could be considered as the policy number

    private LocalDate startDate;
    private Double totalPremiumAmount;
    private Double maturityAmount;
    private Integer numberOfYears;
    private String policyStatus;
    private String annuityTerm;

    // Many-to-one relationship with Customer (already exists)
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // NEW: Many-to-one relationship with Scheme
    @ManyToOne
    @JoinColumn(name = "scheme_id")
    private Scheme scheme;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Double getTotalPremiumAmount() {
        return totalPremiumAmount;
    }

    public void setTotalPremiumAmount(Double totalPremiumAmount) {
        this.totalPremiumAmount = totalPremiumAmount;
    }

    public Double getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(Double maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public Integer getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(Integer numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public String getAnnuityTerm() {
        return annuityTerm;
    }

    public void setAnnuityTerm(String annuityTerm) {
        this.annuityTerm = annuityTerm;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }
}
