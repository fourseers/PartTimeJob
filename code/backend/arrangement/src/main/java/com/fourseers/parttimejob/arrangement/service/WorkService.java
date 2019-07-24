package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.arrangement.projection.WorkProjection;
import org.springframework.data.domain.Page;

public interface WorkService {

    Page<WorkProjection> findPageByUsername(String username, int pageCount, int pageSize);

    Page<WorkProjection> findPageByShopIdAndUsername(int shopId, String username, int pageCount, int pageSize);

    void remark(String username, int workId, int score);
}
