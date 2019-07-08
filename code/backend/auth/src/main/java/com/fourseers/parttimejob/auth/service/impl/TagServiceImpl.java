package com.fourseers.parttimejob.auth.service.impl;

import com.fourseers.parttimejob.auth.dao.TagDao;
import com.fourseers.parttimejob.auth.entity.Tag;
import com.fourseers.parttimejob.auth.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Tag> get(int pageCount, int pageSize) {
        return tagDao.get(pageCount, pageSize).getContent();
    }
}
