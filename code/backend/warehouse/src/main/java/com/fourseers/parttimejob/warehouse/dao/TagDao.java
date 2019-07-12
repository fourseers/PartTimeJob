package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.warehouse.entity.Tag;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Positive;
import java.util.List;

public interface TagDao {

    void save(Tag tag);
    void delete(@Positive Integer id);

    Tag getOne(@Positive Integer id);
    List<Tag> getAll();
    Page<Tag> get(int pageCount, int pageSize);
}
