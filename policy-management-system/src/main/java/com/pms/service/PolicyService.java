package com.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.pms.entity.Policy;
import com.pms.exception.ResourceNotFoundException;
import com.pms.repository.PolicyRepository;
import com.pms.repository.CustomerRepository;
import com.pms.repository.SchemeRepository;

@Service
@Validated
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private SchemeRepository schemeRepository;

    public Policy createPolicy(Policy policy) {
        // Validate if Customer exists (must be provided)
        if (policy.getCustomer() == null || policy.getCustomer().getId() == null ||
            !customerRepository.existsById(policy.getCustomer().getId())) {
            throw new ResourceNotFoundException("Customer not found with id " +
                (policy.getCustomer() != null ? policy.getCustomer().getId() : "null"));
        }

        // Validate if Scheme exists (must be provided)
        if (policy.getScheme() == null || policy.getScheme().getId() == null ||
            !schemeRepository.existsById(policy.getScheme().getId())) {
            throw new ResourceNotFoundException("Scheme not found with id " +
                (policy.getScheme() != null ? policy.getScheme().getId() : "null"));
        }

        // Generate a new policyId by checking the last record
        Policy lastPolicy = policyRepository.findTopByOrderByIdDesc();
        String newPolicyId;
        if (lastPolicy == null || lastPolicy.getPolicyId() == null) {
            newPolicyId = "POLICY001";
        } else {
            // Extract the numeric part after the "POLICY" prefix and increment it
            int lastNumber = Integer.parseInt(lastPolicy.getPolicyId().substring("POLICY".length()));
            int newNumber = lastNumber + 1;
            newPolicyId = String.format("POLICY%03d", newNumber); // Formats as POLICY001, POLICY002, etc.
        }
        
        // Set the auto-generated policyId; user input is ignored for this field
        policy.setPolicyId(newPolicyId);
        
        return policyRepository.save(policy);
    }
    
    public Policy updatePolicy(Long id, Policy policyDetails) {
        // Retrieve the existing policy; throw exception if not found
        Policy existingPolicy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id " + id));

        // Disallow update of restricted fields:
        if (policyDetails.getCustomer() != null) {
            throw new IllegalArgumentException("Customer field is not allowed in policy update.");
        }
        
        if (policyDetails.getScheme() != null) {
            throw new IllegalArgumentException("Scheme field is not allowed in policy update.");
        }
        
        if (policyDetails.getPolicyId() != null) {
            throw new IllegalArgumentException("Policy ID field is not allowed in policy update.");
        }
        
        // Validate allowed fields
        if (policyDetails.getStartDate() == null) {
            throw new IllegalArgumentException("Start date must be provided and be a valid date.");
        }
        
        if (policyDetails.getTotalPremiumAmount() == null || policyDetails.getTotalPremiumAmount() <= 0) {
            throw new IllegalArgumentException("Total premium amount must be a positive number.");
        }
        
        if (policyDetails.getMaturityAmount() == null || policyDetails.getMaturityAmount() <= 0) {
            throw new IllegalArgumentException("Maturity amount must be a positive number.");
        }
        
        if (policyDetails.getNumberOfYears() == null || policyDetails.getNumberOfYears() <= 0) {
            throw new IllegalArgumentException("Number of years must be a positive number.");
        }
        
        if (policyDetails.getPolicyStatus() == null || 
            (!policyDetails.getPolicyStatus().equalsIgnoreCase("Active") &&
             !policyDetails.getPolicyStatus().equalsIgnoreCase("Deactivated"))) {
            throw new IllegalArgumentException("Policy status must be either 'Active' or 'Deactivated'.");
        }
        
        // Update only allowed fields:
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
