package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.warehouse.dao.IndustryDao;
import com.fourseers.parttimejob.warehouse.entity.Industry;
import com.fourseers.parttimejob.warehouse.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class IndustryServiceImpl implements IndustryService {

    @Autowired
    IndustryDao industryDao;

    public void save(Industry industry) {
        industryDao.save(industry);
    }

    public List<Industry> findAll() {
        return industryDao.findAll();
    }
}
