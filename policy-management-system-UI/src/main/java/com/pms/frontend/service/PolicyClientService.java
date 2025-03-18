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

    @Value("${backend.url}")
    private String backendUrl; // e.g., http://localhost:7281/api/policies

    public Policy createPolicy(Policy policy) {
        String url = backendUrl + "/create";
        return restTemplate.postForObject(url, policy, Policy.class);
    }

    public Policy updatePolicy(Long id, Policy policy) {
        String url = backendUrl + "/update/" + id;
        restTemplate.put(url, policy);
        return getPolicyById(id);
    }

    public Policy getPolicyById(Long id) {
        String url = backendUrl + "/" + id;
        return restTemplate.getForObject(url, Policy.class);
    }
    
    public Policy[] getPoliciesBySchemeName(String schemeName) {
        String url = backendUrl + "/scheme/" + schemeName;
        return restTemplate.getForObject(url, Policy[].class);
    }

    public Policy[] getPoliciesByDate(String date) {
        String url = backendUrl + "/date/" + date;
        return restTemplate.getForObject(url, Policy[].class);
    }

    public Policy[] getPoliciesByPremiumRange(Double min, Double max) {
        String url = backendUrl + "/premium?min=" + min + "&max=" + max;
        return restTemplate.getForObject(url, Policy[].class);
    }

    public Policy[] getPoliciesByMaturityRange(Double min, Double max) {
        String url = backendUrl + "/maturity?min=" + min + "&max=" + max;
        return restTemplate.getForObject(url, Policy[].class);
    }

    public Policy[] getPoliciesByYears(Integer years) {
        String url = backendUrl + "/years/" + years;
        return restTemplate.getForObject(url, Policy[].class);
    }

    //deactivate method
    public Policy deactivatePolicy(Long id) {
        Policy updatePayload = new Policy();
        // Only update the policyStatus to "Deactivated"
        updatePayload.setPolicyStatus("Deactivated");
        // Other allowed fields are left null so that the backend update logic will preserve the existing values.
        return updatePolicy(id, updatePayload);
    }
}
