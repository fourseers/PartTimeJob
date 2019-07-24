package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.common.entity.CV;
import com.fourseers.parttimejob.warehouse.projection.CVBriefProjection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CVRepository extends MongoRepository<CV, String> {

    List<CVBriefProjection> getCVByUserId(int userId);
}
