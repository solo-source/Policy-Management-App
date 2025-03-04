package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.entity.Scheme;

@Repository
public interface SchemeRepository extends JpaRepository<Scheme, Long> {
}
