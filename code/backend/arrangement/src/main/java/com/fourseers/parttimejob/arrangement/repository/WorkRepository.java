package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Integer> {

    Work getByJobAndWorker(Job job, WechatUser wechatUser);
}
