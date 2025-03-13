package com.pms.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.pms.frontend.model.Policy;

@Service
public class PolicyClientService {

    @Autowired
    private RestTemplate restTemplate;

    // The base URL of your backend, e.g. "http://localhost:8080/api/policies"
    @Value("${backend.url}")
    private String backendUrl;

    // CREATE a new policy
    public Policy createPolicy(Policy policy) {
        String url = backendUrl + "/create";
        return restTemplate.postForObject(url, policy, Policy.class);
    }

    // UPDATE an existing policy
    public Policy updatePolicy(Long id, Policy policy) {
        String url = backendUrl + "/update/" + id;
        restTemplate.put(url, policy);
        return getPolicyById(id);
    }

    // GET policy by ID
    public Policy getPolicyById(Long id) {
        String url = backendUrl + "/" + id;
        return restTemplate.getForObject(url, Policy.class);
    }

    // Reuse update logic for "deactivation" by simply setting policyStatus to "Deactivated"
    public Policy deactivatePolicy(Long id) {
        // 1) Fetch the current policy
        Policy existingPolicy = getPolicyById(id);

        // 2) Modify its status
        existingPolicy.setPolicyStatus("Deactivated");

        // 3) Reuse the update logic
        return updatePolicy(id, existingPolicy);
    }
}