package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.common.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
