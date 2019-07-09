package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.warehouse.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> get(int pageCount, int pageSize);
    List<Tag> getAll();
}
