package com.pms.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "schemes")
public class Scheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String schemeName;

    @Column
    private String description;

    @Column
    private String benefits;

    @Column
    private String eligibilityCriteria;

    // One-to-many relationship with Policy
    @OneToMany(mappedBy = "scheme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Policy> policies;

    // Default constructor
    public Scheme() {
    }

    // Parameterized constructor
    public Scheme(String schemeName, String description, String benefits, String eligibilityCriteria) {
        this.schemeName = schemeName;
        this.description = description;
        this.benefits = benefits;
        this.eligibilityCriteria = eligibilityCriteria;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
}
