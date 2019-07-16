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

    @Query(nativeQuery = true,
            value = "SELECT *, abs(shop.longitude - ?1) + abs(shop.latitude - ?2) as dis " +
            "FROM job INNER JOIN shop on job.shop_shop_id = shop.shop_id ORDER BY dis ASC",
    countQuery = "SELECT COUNT(*) FROM job INNER JOIN shop ON job.shop_shop_id = shop.shop_id")
    Page<Job> findByGeoLocation(float longitude, float latitude, PageRequest pageRequest);
}
