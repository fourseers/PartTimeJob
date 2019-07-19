package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.common.entity.Tag;
import org.springframework.data.domain.Page;

public interface TagService {

    void addOne(Tag tag);

    Page<Tag> get(int pageCount, int pageSize);
}
