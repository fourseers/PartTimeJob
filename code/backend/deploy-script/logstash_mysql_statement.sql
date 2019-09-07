select
    job.*,
    GROUP_CONCAT(tag.name SEPARATOR ', ') as tags,
    CONCAT(shop.latitude, ', ', shop.longitude) as location
from job
inner join job_tag_list
    on job.job_id = job_tag_list.job_list_job_id
inner join tag
    on job_tag_list.tag_list_id = tag.id
inner join shop
    on job.shop_shop_id = shop.shop_id
where job.job_id > :sql_last_value
group by job.job_id;
