package com.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.pms.entity.Policy;
import com.pms.exception.ResourceNotFoundException;
import com.pms.repository.PolicyRepository;
import com.pms.repository.CustomerRepository;
import com.pms.repository.SchemeRepository;
import com.pms.validation.OnUpdate;

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
        
        // Validate numeric and other fields
        if (policy.getStartDate() == null) {
            throw new IllegalArgumentException("Start date must be provided and be a valid date.");
        }
        // Since totalPremiumAmount is a Double, any non-numeric input will be caught during deserialization.
        if (policy.getTotalPremiumAmount() == null || policy.getTotalPremiumAmount() <= 0) {
            throw new IllegalArgumentException("Total premium amount must be a positive number.");
        }
        // Similar check for maturityAmount.
        if (policy.getMaturityAmount() == null || policy.getMaturityAmount() <= 0) {
            throw new IllegalArgumentException("Maturity amount must be a positive number.");
        }
        // And for numberOfYears (an Integer).
        if (policy.getNumberOfYears() == null || policy.getNumberOfYears() <= 0) {
            throw new IllegalArgumentException("Number of years must be a positive number.");
        }
        if (policy.getPolicyStatus() == null || 
            (!policy.getPolicyStatus().equalsIgnoreCase("Active") &&
             !policy.getPolicyStatus().equalsIgnoreCase("Deactivated"))) {
            throw new IllegalArgumentException("Policy status must be either 'Active' or 'Deactivated'.");
        }
        if (policy.getAnnuityTerm() == null ||
            (!policy.getAnnuityTerm().equalsIgnoreCase("Yearly") &&
             !policy.getAnnuityTerm().equalsIgnoreCase("Monthly"))) {
            throw new IllegalArgumentException("Annuity Term must be either 'Yearly' or 'Monthly'.");
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
        
        // Set the auto-generated policyId; user input is ignored for this field.
        policy.setPolicyId(newPolicyId);
        
        return policyRepository.save(policy);
    }
    
    public Policy updatePolicy(Long id, Policy policyDetails) {
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
        
        // Validate allowed fields (numeric checks will also prevent non-numeric inputs due to type conversion):
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
        if (policyDetails.getAnnuityTerm() == null ||
            (!policyDetails.getAnnuityTerm().equalsIgnoreCase("Yearly") &&
             !policyDetails.getAnnuityTerm().equalsIgnoreCase("Monthly"))) {
            throw new IllegalArgumentException("Annuity Term must be either 'Yearly' or 'Monthly'.");
        }
        
        // Update only allowed fields:
        existingPolicy.setStartDate(policyDetails.getStartDate());
        existingPolicy.setTotalPremiumAmount(policyDetails.getTotalPremiumAmount());
        existingPolicy.setMaturityAmount(policyDetails.getMaturityAmount());
        existingPolicy.setNumberOfYears(policyDetails.getNumberOfYears());
        existingPolicy.setPolicyStatus(policyDetails.getPolicyStatus());
        existingPolicy.setAnnuityTerm(policyDetails.getAnnuityTerm());

        return policyRepository.save(existingPolicy);
    }
    
    // View policy details by id; throw exception if not found.
    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id " + id));
    }
}
