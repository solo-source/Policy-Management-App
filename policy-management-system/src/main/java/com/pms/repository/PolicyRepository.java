package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.model.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    // Additional query methods (if needed) can be defined here
}