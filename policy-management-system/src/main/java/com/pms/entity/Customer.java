package com.pms.entity;

import jakarta.persistence.*;
//import java.time.LocalDate;

@Entity
@Table(name = "customers") // Ensure this matches the actual table name in your DB
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key in the customers table

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = false)
    private String email;

    // Optionally, add additional fields (e.g., phone, address) as needed:
    // private String phone;
    // private String address;

    // Default constructor
    public Customer() {
    }

    // Parameterized constructor (optional)
    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and Setters

    // Getter and Setter for id
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
