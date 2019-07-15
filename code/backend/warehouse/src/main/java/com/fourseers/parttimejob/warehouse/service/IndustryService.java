package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.common.entity.Industry;

import java.util.List;

public interface IndustryService {

    void save(Industry industry);

    List<Industry> findAll();
}
