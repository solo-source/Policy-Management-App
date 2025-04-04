package com.pms.frontend.model;

import java.time.LocalDate;

public class Policy {
    private Long id;
    private String policyId;
    private LocalDate startDate;
    private Double totalPremiumAmount;
    private Double maturityAmount;
    private Integer numberOfYears;
    private String policyStatus;  // "Active" or "Deactivated"
    private String annuityTerm;   // "Yearly" or "Monthly"
    
    // Nested objects for required fields on creation
    private Customer customer;
    private Scheme scheme;
    
    // Getters and setters

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
