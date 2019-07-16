package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {

    Job findByJobId(int jobId);

    List<Job> findByShop(Shop shop);

    @Query(value = "SELECT j, abs(s.longitude - ?1) + abs(s.latitude - ?2) as dis " +
            "FROM Job j INNER JOIN FETCH j.shop as s",
    countQuery = "FROM Job j")
    Page<Job> findByGeoLocation(float longitude, float latitude, PageRequest pageRequest);
}
