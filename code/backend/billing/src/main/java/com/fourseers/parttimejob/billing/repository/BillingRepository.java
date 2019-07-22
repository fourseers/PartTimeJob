package com.fourseers.parttimejob.billing.repository;

import com.fourseers.parttimejob.common.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing, Integer> {
}
