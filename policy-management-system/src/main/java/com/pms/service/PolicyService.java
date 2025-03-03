package com.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.entity.Policy;
import com.pms.exception.ResourceNotFoundException;
import com.pms.repository.PolicyRepository;
import com.pms.repository.CustomerRepository;
import com.pms.repository.SchemeRepository;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private SchemeRepository schemeRepository;

    public Policy createPolicy(Policy policy) {
        // Validate if Customer exists
        if (policy.getCustomer() == null || policy.getCustomer().getId() == null ||
            !customerRepository.existsById(policy.getCustomer().getId())) {
            throw new ResourceNotFoundException("Customer not found with id " +
                (policy.getCustomer() != null ? policy.getCustomer().getId() : "null"));
        }

        // Validate if Scheme exists
        if (policy.getScheme() == null || policy.getScheme().getId() == null ||
            !schemeRepository.existsById(policy.getScheme().getId())) {
            throw new ResourceNotFoundException("Scheme not found with id " +
                (policy.getScheme() != null ? policy.getScheme().getId() : "null"));
        }
        
        // Process and save the policy
        return policyRepository.save(policy);
    }
    
    public Policy updatePolicy(Long id, Policy policyDetails) {
        // Retrieve the existing policy; throw exception if not found
        Policy existingPolicy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id " + id));

        // Ensure that the restricted fields are not being updated
        // Check customer: if provided, it must match the existing policy's customer
        if (policyDetails.getCustomer() != null && policyDetails.getCustomer().getId() != null &&
            !policyDetails.getCustomer().getId().equals(existingPolicy.getCustomer().getId())) {
            throw new IllegalArgumentException("Cannot update customer id for the policy.");
        }
        
        // Check scheme: if provided, it must match the existing policy's scheme
        if (policyDetails.getScheme() != null && policyDetails.getScheme().getId() != null &&
            !policyDetails.getScheme().getId().equals(existingPolicy.getScheme().getId())) {
            throw new IllegalArgumentException("Cannot update scheme id for the policy.");
        }
        
        // Check policyId: if provided, it must match the existing policy's policyId
        if (policyDetails.getPolicyId() != null && 
            !policyDetails.getPolicyId().equals(existingPolicy.getPolicyId())) {
            throw new IllegalArgumentException("Cannot update policy id.");
        }
        
        // Validate start date
        if (policyDetails.getStartDate() == null) {
            throw new IllegalArgumentException("Start date must be provided and be a valid date.");
        }
        
        // Validate total premium amount
        if (policyDetails.getTotalPremiumAmount() == null || policyDetails.getTotalPremiumAmount() <= 0) {
            throw new IllegalArgumentException("Total premium amount must be a positive number.");
        }
        
        // Validate maturity amount
        if (policyDetails.getMaturityAmount() == null || policyDetails.getMaturityAmount() <= 0) {
            throw new IllegalArgumentException("Maturity amount must be a positive number.");
        }
        
        // Validate number of years
        if (policyDetails.getNumberOfYears() == null || policyDetails.getNumberOfYears() <= 0) {
            throw new IllegalArgumentException("Number of years must be a positive number.");
        }
        
        // Validate policy status: must be "Active" or "Deactivated"
        if (policyDetails.getPolicyStatus() == null || 
            (!policyDetails.getPolicyStatus().equalsIgnoreCase("Active") &&
             !policyDetails.getPolicyStatus().equalsIgnoreCase("Deactivated"))) {
            throw new IllegalArgumentException("Policy status must be either 'Active' or 'Deactivated'.");
        }
        
        // Only update allowed fields:
        // Do not update policyId, customer, or scheme.
        existingPolicy.setStartDate(policyDetails.getStartDate());
        existingPolicy.setTotalPremiumAmount(policyDetails.getTotalPremiumAmount());
        existingPolicy.setMaturityAmount(policyDetails.getMaturityAmount());
        existingPolicy.setNumberOfYears(policyDetails.getNumberOfYears());
        existingPolicy.setPolicyStatus(policyDetails.getPolicyStatus());
        existingPolicy.setAnnuityTerm(policyDetails.getAnnuityTerm());

        // Save and return the updated policy
        return policyRepository.save(existingPolicy);
    }
    
    // View policy details by id; throw exception if not found
    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id " + id));
    }
}
