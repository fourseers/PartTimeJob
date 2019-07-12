package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.warehouse.entity.Industry;

import java.util.List;

public interface IndustryDao {

    void save(Industry industry);

    List<Industry> findAll();
}
