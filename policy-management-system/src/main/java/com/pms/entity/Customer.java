package com.pms.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    // New optional fields
    @Column
    private String phoneNumber;

    @Column
    private String address;

    // One-to-many relationship with Policy
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Policy> policies;

    // Default constructor
    public Customer() {
    }

    // Parameterized constructor
    public Customer(String name, String email, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
  
    public String getPhoneNumber() {
        return phoneNumber;
    }
  
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
  
    public String getAddress() {
        return address;
    }
  
    public void setAddress(String address) {
        this.address = address;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
}
