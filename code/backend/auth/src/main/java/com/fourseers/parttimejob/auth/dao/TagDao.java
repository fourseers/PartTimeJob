package com.fourseers.parttimejob.auth.dao;

import com.fourseers.parttimejob.auth.entity.Tag;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Positive;

public interface TagDao {

    void save(Tag tag);
    void delete(@Positive Integer id);

    Tag getOne(@Positive Integer id);
    Page<Tag> get(int pageCount, int pageSize);
}
