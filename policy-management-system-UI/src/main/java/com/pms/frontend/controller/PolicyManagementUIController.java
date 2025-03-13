package com.pms.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.pms.frontend.model.Policy;
import com.pms.frontend.service.PolicyClientService;

@Controller
@RequestMapping("/policy-management")
public class PolicyManagementUIController {

    @Autowired
    private PolicyClientService policyClientService;

    @GetMapping
    public String showPolicyManagementPage(Model model) {
        model.addAttribute("newPolicy", new Policy());
        model.addAttribute("updatePolicy", new Policy());
        model.addAttribute("policyResult", null);
        // Other attributes as needed
        return "policyManagement";
    }

    @PostMapping("/create")
    public String createPolicy(@ModelAttribute("newPolicy") Policy newPolicy, Model model) {
        try {
            Policy createdPolicy = policyClientService.createPolicy(newPolicy);
            model.addAttribute("message", "Policy created successfully with ID: " + createdPolicy.getPolicyId());
        } catch (Exception ex) {
            model.addAttribute("error", "Error creating policy: " + ex.getMessage());
        }
        return "redirect:/policy-management";
    }

    // Handle update form submission
    @PostMapping("/update")
    public String updatePolicy(@RequestParam("policyIdToUpdate") Long id,
                               @ModelAttribute("updatePolicy") Policy updatePolicy,
                               Model model) {
        // ID is taken from a separate input field
        Policy updated = policyClientService.updatePolicy(id, updatePolicy);
        model.addAttribute("message", "Policy updated successfully for ID: " + updated.getPolicyId());
        return "redirect:/policy-management";
    }

    // Handle deactivation form submission
    @PostMapping("/deactivate")
    public String deactivatePolicy(@RequestParam("policyIdToDeactivate") Long id, Model model) {
        // 1) Call the client service
        Policy deactivated = policyClientService.deactivatePolicy(id);

        // 2) Provide a success message
        model.addAttribute("message", "Policy deactivated successfully for ID: " + deactivated.getPolicyId());

        // 3) Redirect or return the same page
        return "redirect:/policy-management";
    }

    // Handle view form submission
    @PostMapping("/view")
    public String viewPolicy(@RequestParam("policyIdToView") Long id, Model model) {
        Policy policy = policyClientService.getPolicyById(id);
        model.addAttribute("policyResult", policy);
        model.addAttribute("viewPolicyId", id);
        // We still need the forms to be available
        model.addAttribute("newPolicy", new Policy());
        model.addAttribute("updatePolicy", new Policy());
        model.addAttribute("deactivatePolicy", new Policy());
        return "policyManagement";
    }
}
