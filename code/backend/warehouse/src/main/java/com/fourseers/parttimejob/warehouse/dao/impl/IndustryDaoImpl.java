package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.warehouse.dao.IndustryDao;
import com.fourseers.parttimejob.common.entity.Industry;
import com.fourseers.parttimejob.warehouse.repository.IndustryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IndustryDaoImpl implements IndustryDao {

    @Autowired
    private IndustryRepository industryRepository;

    public void save(Industry industry) {
        industryRepository.save(industry);
    }

    public List<Industry> findAll() {
        return industryRepository.findAll();
    }
}
