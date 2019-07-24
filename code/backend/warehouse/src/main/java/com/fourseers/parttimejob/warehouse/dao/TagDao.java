package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.common.entity.Tag;

import javax.validation.constraints.Positive;
import java.util.List;

public interface TagDao {

    void save(Tag tag);
    void delete(@Positive Integer id);

    Tag getOne(@Positive Integer id);
    List<Tag> findAll();
}
