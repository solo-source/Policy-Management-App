package com.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pms.entity.Policy;
import com.pms.service.PolicyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @PostMapping("/create")
    public ResponseEntity<Policy> createPolicy(@Valid @RequestBody Policy policy) {
        Policy createdPolicy = policyService.createPolicy(policy);
        return ResponseEntity.ok(createdPolicy);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long id, @Valid @RequestBody Policy policyDetails) {
        Policy updatedPolicy = policyService.updatePolicy(id, policyDetails);
        return ResponseEntity.ok(updatedPolicy);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) {
        Policy policy = policyService.getPolicyById(id);
        return ResponseEntity.ok(policy);
    }
}
