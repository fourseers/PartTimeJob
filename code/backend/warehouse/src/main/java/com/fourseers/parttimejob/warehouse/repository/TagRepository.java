package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.warehouse.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
