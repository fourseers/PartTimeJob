package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.warehouse.dao.TagDao;
import com.fourseers.parttimejob.warehouse.entity.Tag;
import com.fourseers.parttimejob.warehouse.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;


    @Override
    public void addOne(Tag tag) {
        tagDao.save(tag);
    }

    @Override
    public List<Tag> get(int pageCount, int pageSize) {
        return tagDao.get(pageCount, pageSize).getContent();
    }

    @Override
    public List<Tag> getAll() {
        return tagDao.getAll();
    }
}
