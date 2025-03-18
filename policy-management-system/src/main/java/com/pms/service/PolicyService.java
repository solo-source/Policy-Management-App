package com.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.pms.entity.Policy;
import com.pms.exception.ResourceNotFoundException;
import com.pms.repository.PolicyRepository;
import com.pms.repository.CustomerRepository;
import com.pms.repository.SchemeRepository;
import java.time.LocalDate;
import java.util.List;

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

        // Update allowed fields if provided; skip if value is empty

        if (policyDetails.getStartDate() != null) {
            existingPolicy.setStartDate(policyDetails.getStartDate());
        }

        if (policyDetails.getTotalPremiumAmount() != null) {
            if (policyDetails.getTotalPremiumAmount() <= 0) {
                throw new IllegalArgumentException("Total premium amount must be a positive number.");
            }
            existingPolicy.setTotalPremiumAmount(policyDetails.getTotalPremiumAmount());
        }

        if (policyDetails.getMaturityAmount() != null) {
            if (policyDetails.getMaturityAmount() <= 0) {
                throw new IllegalArgumentException("Maturity amount must be a positive number.");
            }
            existingPolicy.setMaturityAmount(policyDetails.getMaturityAmount());
        }

        if (policyDetails.getNumberOfYears() != null) {
            if (policyDetails.getNumberOfYears() <= 0) {
                throw new IllegalArgumentException("Number of years must be a positive number.");
            }
            existingPolicy.setNumberOfYears(policyDetails.getNumberOfYears());
        }

        if (policyDetails.getPolicyStatus() != null) {
            String status = policyDetails.getPolicyStatus().trim();
            // If the trimmed value is not empty, validate and update; otherwise skip
            if (!status.isEmpty()) {
                if (!(status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Deactivated"))) {
                    throw new IllegalArgumentException("Policy status must be either 'Active' or 'Deactivated'.");
                }
                existingPolicy.setPolicyStatus(status);
            }
        }

        if (policyDetails.getAnnuityTerm() != null) {
            String term = policyDetails.getAnnuityTerm().trim();
            if (!term.isEmpty()) {
                if (!(term.equalsIgnoreCase("Yearly") || term.equalsIgnoreCase("Monthly"))) {
                    throw new IllegalArgumentException("Annuity Term must be either 'Yearly' or 'Monthly'.");
                }
                existingPolicy.setAnnuityTerm(term);
            }
        }

        return policyRepository.save(existingPolicy);
    }
    
    // View policy details by id; throw exception if not found.
    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id " + id));
    }
    
 // New methods for view operations:
    public List<Policy> getPoliciesBySchemeName(String schemeName) {
        return policyRepository.findBySchemeSchemeName(schemeName);
    }
    
    public List<Policy> getPoliciesByStartDate(LocalDate startDate) {
        return policyRepository.findByStartDate(startDate);
    }
    
    public List<Policy> getPoliciesByPremiumAmountRange(Double min, Double max) {
        return policyRepository.findByTotalPremiumAmountBetween(min, max);
    }
    
    public List<Policy> getPoliciesByMaturityAmountRange(Double min, Double max) {
        return policyRepository.findByMaturityAmountBetween(min, max);
    }
    
    public List<Policy> getPoliciesByNumberOfYears(Integer years) {
        return policyRepository.findByNumberOfYears(years);
    }
}
