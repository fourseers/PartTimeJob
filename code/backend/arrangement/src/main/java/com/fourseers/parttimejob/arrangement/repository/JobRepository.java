package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories("com.fourseers.parttimejob.common.entity.*")
@EntityScan(value={"com.fourseers.parttimejob.common.entity.*"})
public interface JobRepository extends JpaRepository<Job, Integer> {

    Job findByJobId(int jobId);

    List<Job> findByShop(Shop shop);
}
