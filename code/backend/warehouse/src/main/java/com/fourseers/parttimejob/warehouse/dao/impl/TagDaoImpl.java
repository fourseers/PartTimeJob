package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.common.entity.Tag;
import com.fourseers.parttimejob.warehouse.dao.TagDao;
import com.fourseers.parttimejob.warehouse.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Component
public class TagDaoImpl implements TagDao {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public void delete(@Positive Integer id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag getOne(@Positive Integer id) {
        Optional<Tag> result = tagRepository.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public Page<Tag> get(int pageCount, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageCount, pageSize);
        return tagRepository.findAll(pageRequest);
    }
}
