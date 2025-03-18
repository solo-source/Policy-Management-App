package com.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.pms.entity.Policy;
import com.pms.service.PolicyService;
import com.pms.validation.OnCreate;
import com.pms.validation.OnUpdate;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
@CrossOrigin(origins = "http://localhost:8081")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @PostMapping("/create")
    public ResponseEntity<Policy> createPolicy(@Validated(OnCreate.class) @RequestBody Policy policy) {
        Policy createdPolicy = policyService.createPolicy(policy);
        return ResponseEntity.ok(createdPolicy);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long id, 
                                               @Validated(OnUpdate.class) @RequestBody Policy policy) {
        Policy updatedPolicy = policyService.updatePolicy(id, policy);
        return ResponseEntity.ok(updatedPolicy);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) {
        Policy policy = policyService.getPolicyById(id);
        return ResponseEntity.ok(policy);
    }
    
    @GetMapping("/scheme/{schemeName}")
    public ResponseEntity<List<Policy>> getPoliciesByScheme(@PathVariable String schemeName) {
        List<Policy> policies = policyService.getPoliciesBySchemeName(schemeName);
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Policy>> getPoliciesByDate(@PathVariable String date) {
        LocalDate d = LocalDate.parse(date);
        List<Policy> policies = policyService.getPoliciesByStartDate(d);
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/premium")
    public ResponseEntity<List<Policy>> getPoliciesByPremium(@RequestParam Double min,
                                                             @RequestParam Double max) {
        List<Policy> policies = policyService.getPoliciesByPremiumAmountRange(min, max);
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/maturity")
    public ResponseEntity<List<Policy>> getPoliciesByMaturity(@RequestParam Double min,
                                                              @RequestParam Double max) {
        List<Policy> policies = policyService.getPoliciesByMaturityAmountRange(min, max);
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/years/{years}")
    public ResponseEntity<List<Policy>> getPoliciesByYears(@PathVariable Integer years) {
        List<Policy> policies = policyService.getPoliciesByNumberOfYears(years);
        return ResponseEntity.ok(policies);
    }
}
