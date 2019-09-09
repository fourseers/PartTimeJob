package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.common.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}
