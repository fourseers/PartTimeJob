package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.fourseers.parttimejob.common.entity.*")
@EntityScan(value={"com.fourseers.parttimejob.common.entity.*"})
public interface JobRepository extends JpaRepository<Job, Integer> {

    Job findByJobId(int jobId);

    Page<Job> findPageByShopOrderByJobIdDesc(Shop shop, Pageable pageable);

    @Query("select job from Shop shop inner join Job job on shop = job.shop where shop.company = ?1 order by job.jobId desc")
    Page<Job> findPageByCompany(Company company, Pageable pageable);
}
