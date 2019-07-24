package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.common.entity.CV;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CVRepository extends MongoRepository<CV, String> {
}
