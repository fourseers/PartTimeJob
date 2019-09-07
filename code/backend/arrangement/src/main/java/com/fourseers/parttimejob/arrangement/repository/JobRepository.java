package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories("com.fourseers.parttimejob.common.entity.*")
@EntityScan(value={"com.fourseers.parttimejob.common.entity.*"})
public interface JobRepository extends JpaRepository<Job, Integer>, JpaSpecificationExecutor<Job> {

    Job findByJobId(int jobId);

    // query is too much, so why not just leave it here
    List<JobDetailedInfoProjection> findJobsByIdentifier(String identifier);

    List<Job> findByShop(Shop shop);

    Page<Job> findPageByShopOrderByJobIdDesc(Shop shop, Pageable pageable);

    @Query("select job from Shop shop inner join Job job on shop = job.shop where shop.company = ?1 order by job.jobId desc")
    Page<Job> findPageByCompany(Company company, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT *," +
                    " GROUP_CONCAT(tag.name SEPARATOR ', ') as tags" +
                    " FROM job" +
                    " INNER JOIN shop ON shop.shop_id = job.shop_shop_id" +
                    " INNER JOIN job_tag_list ON job.job_id = job_tag_list.job_list_job_id" +
                    " INNER JOIN tag ON tag.id = job_tag_list.tag_list_id" +
                    " WHERE job.manual_stop = false" +
                    " AND job.end_apply_time >= NOW()" +
                    " AND DATE_ADD(NOW(), INTERVAL :daysToCome DAY) >= job.begin_date" +
                    " AND job.salary between :minSalary and :maxSalary" +
                    " GROUP BY job.identifier" +
                    " HAVING tags LIKE %:tag%" +
                    " ORDER BY job.salary DESC ",
        countQuery = "SELECT COUNT(job.identifier)" +
                " FROM job" +
                " INNER JOIN job_tag_list ON job.job_id = job_tag_list.job_list_job_id" +
                " INNER JOIN tag ON tag.id = job_tag_list.tag_list_id" +
                " WHERE job.manual_stop = false" +
                " AND job.end_apply_time >= NOW()" +
                " AND DATE_ADD(NOW(), INTERVAL :daysToCome DAY) >= job.begin_date" +
                " AND job.salary between :minSalary and :maxSalary" +
                " GROUP BY job.identifier" +
                " HAVING GROUP_CONCAT(tag.name SEPARATOR ', ') LIKE %:tag%")
    Page<Job> queryJob(Integer daysToCome, Double minSalary, Double maxSalary, String tag, Pageable pageRequest);

    @Query(nativeQuery = true,
            value = "SELECT *," +
                    " abs(shop.longitude - :longitude ) + abs(shop.latitude - :latitude ) as dis," +
                    " GROUP_CONCAT(tag.name SEPARATOR ', ') as tags" +
                    " FROM job" +
                    " INNER JOIN shop ON shop.shop_id = job.shop_shop_id" +
                    " INNER JOIN job_tag_list ON job.job_id = job_tag_list.job_list_job_id" +
                    " INNER JOIN tag ON tag.id = job_tag_list.tag_list_id" +
                    " WHERE job.manual_stop = false" +
                    " AND job.end_apply_time >= NOW()" +
                    " AND DATE_ADD(NOW(), INTERVAL :daysToCome DAY) >= job.begin_date" +
                    " AND job.salary between :minSalary and :maxSalary" +
                    " GROUP BY job.identifier" +
                    " HAVING tags LIKE %:tag%" +
                    " AND dis <= :geoRange" +
                    " ORDER BY dis ASC ",
            countQuery = "SELECT COUNT(job.identifier)" +
                    " FROM job" +
                    " INNER JOIN shop ON shop.shop_id = job.shop_shop_id" +
                    " INNER JOIN job_tag_list ON job.job_id = job_tag_list.job_list_job_id" +
                    " INNER JOIN tag ON tag.id = job_tag_list.tag_list_id" +
                    " WHERE job.manual_stop = false" +
                    " AND job.end_apply_time >= NOW()" +
                    " AND DATE_ADD(NOW(), INTERVAL :daysToCome DAY) >= job.begin_date" +
                    " AND job.salary between :minSalary and :maxSalary" +
                    " AND abs(shop.longitude - :longitude ) + abs(shop.latitude - :latitude ) <= :geoRange" +
                    " GROUP BY job.identifier" +
                    " HAVING GROUP_CONCAT(tag.name SEPARATOR ', ') LIKE %:tag%")
    Page<Job> queryJobByGeoLocation(Double longitude, Double latitude, Double geoRange,
                                    Integer daysToCome, Double minSalary, Double maxSalary, String tag, Pageable pageRequest);
}
