package com.fourseers.parttimejob.arrangement.config;

import com.fourseers.parttimejob.arrangement.projection.WorkNotifyProjection;
import com.fourseers.parttimejob.arrangement.service.WorkService;
import com.fourseers.parttimejob.arrangement.util.AliSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableAsync
public class Scheduler {

    @Autowired
    WorkService workService;

    @Autowired
    AliSms aliSms;

    @Async
    @Scheduled(fixedRate = 60000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {

        System.err.println("Fixed rate task async - " + System.currentTimeMillis() / 1000);
        List<WorkNotifyProjection> notCheckedIn = workService.getNotCheckedIn();
        for (WorkNotifyProjection work : notCheckedIn) {
            aliSms.send(work.getPhone(), work.getUsername(), work.getTime().toString(), work.getJobname(), "上班");
        }

        List<WorkNotifyProjection> notCheckedOut = workService.getNotCheckedOut();
        for (WorkNotifyProjection work : notCheckedOut) {
            aliSms.send(work.getPhone(), work.getUsername(), work.getTime().toString(), work.getJobname(), "下班");
        }
    }
}
