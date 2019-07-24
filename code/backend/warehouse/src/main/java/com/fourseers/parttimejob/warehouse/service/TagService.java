package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.common.entity.Tag;

import java.util.List;

public interface TagService {

    void addOne(Tag tag);

    List<Tag> findAll();
}
