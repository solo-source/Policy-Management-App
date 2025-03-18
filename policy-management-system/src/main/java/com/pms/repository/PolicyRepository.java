package com.pms.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pms.entity.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Policy findTopByOrderByIdDesc();
    
    // New methods:
    List<Policy> findBySchemeSchemeName(String schemeName);
    List<Policy> findByStartDate(LocalDate startDate);
    List<Policy> findByTotalPremiumAmountBetween(Double min, Double max);
    List<Policy> findByMaturityAmountBetween(Double min, Double max);
    List<Policy> findByNumberOfYears(Integer numberOfYears);
}
