package com.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.entity.Policy;
import com.pms.repository.PolicyRepository;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public Policy createPolicy(Policy policy) {
        // Validate and process the policy data if needed
        return policyRepository.save(policy);
    }
    
    public Policy updatePolicy(Long id, Policy policyDetails) {
        // Retrieve the existing policy; if not found, .get() will throw NoSuchElementException
        Policy existingPolicy = policyRepository.findById(id).get();

        // Update fields (modify as necessary)
        existingPolicy.setPolicyId(policyDetails.getPolicyId());
        existingPolicy.setStartDate(policyDetails.getStartDate());
        existingPolicy.setTotalPremiumAmount(policyDetails.getTotalPremiumAmount());
        existingPolicy.setMaturityAmount(policyDetails.getMaturityAmount());
        existingPolicy.setNumberOfYears(policyDetails.getNumberOfYears());
        existingPolicy.setPolicyStatus(policyDetails.getPolicyStatus());
        existingPolicy.setAnnuityTerm(policyDetails.getAnnuityTerm());
        existingPolicy.setCustomer(policyDetails.getCustomer());
        existingPolicy.setScheme(policyDetails.getScheme());

        // Save and return the updated policy
        return policyRepository.save(existingPolicy);
    }
    
    // View policy details by id; if not found, .get() will throw NoSuchElementException
    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id).get();
    }
}
