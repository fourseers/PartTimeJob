package com.fourseers.parttimejob.arrangement.dao.impl;

import com.fourseers.parttimejob.arrangement.dao.CVDao;
import com.fourseers.parttimejob.arrangement.repository.CVRepository;
import com.fourseers.parttimejob.common.entity.CV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CVDaoImpl implements CVDao {

    @Autowired
    private CVRepository cvRepository;

    @Override
    public CV getOne(String cvId) {
        Optional<CV> result = cvRepository.findById(cvId);
        return result.isPresent() ? result.get() : null;
    }
}
