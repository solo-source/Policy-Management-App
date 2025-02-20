package com.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.model.Policy;
import com.pms.repository.PolicyRepository;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public Policy createPolicy(Policy policy) {
        // Validate and process the policy data if needed
        return policyRepository.save(policy);
    }
    
    // Methods for update, closure, and view need to be created
}