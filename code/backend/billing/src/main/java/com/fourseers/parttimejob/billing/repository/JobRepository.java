package com.fourseers.parttimejob.billing.repository;

import com.fourseers.parttimejob.common.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

// test purposes only
public interface JobRepository extends JpaRepository<Job, Integer> {
}
