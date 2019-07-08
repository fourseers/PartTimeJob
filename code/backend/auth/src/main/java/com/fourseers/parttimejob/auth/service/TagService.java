package com.fourseers.parttimejob.auth.service;

import com.fourseers.parttimejob.auth.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> get(int pageCount, int pageSize);
}
