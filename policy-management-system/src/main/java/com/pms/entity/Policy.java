package com.pms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This is the policy number (not updateable)
    @Column(nullable = false, unique = true)
    @NotNull(message = "Policy ID is required")
    private String policyId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "Total premium amount is required")
    @Positive(message = "Total premium amount must be positive")
    private Double totalPremiumAmount;
    
    @NotNull(message = "Maturity amount is required")
    @Positive(message = "Maturity amount must be positive")
    private Double maturityAmount;
    
    @NotNull(message = "Number of years is required")
    @Positive(message = "Number of years must be positive")
    private Integer numberOfYears;
    
    @NotNull(message = "Policy status is required")
    @Pattern(regexp = "Active|Deactivated", message = "Policy status must be either 'Active' or 'Deactivated'")
    private String policyStatus;
    
    private String annuityTerm;

    // Many-to-one relationship with Customer (read-only for this service)
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull(message = "Customer is required")
    private Customer customer;

    // Many-to-one relationship with Scheme (read-only for this service)
    @ManyToOne
    @JoinColumn(name = "scheme_id")
    @NotNull(message = "Scheme is required")
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
