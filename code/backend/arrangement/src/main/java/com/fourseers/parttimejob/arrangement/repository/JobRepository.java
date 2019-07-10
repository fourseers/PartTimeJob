package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.arrangement.entity.Job;
import com.fourseers.parttimejob.arrangement.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {

    Job findByJobId(int jobId);

    List<Job> findByShop(Shop shop);
}
