package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.entity.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}