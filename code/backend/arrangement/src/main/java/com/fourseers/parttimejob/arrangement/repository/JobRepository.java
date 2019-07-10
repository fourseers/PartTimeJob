package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.arrangement.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {

    Job findByJobId(int jobId);
}
