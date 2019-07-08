package com.fourseers.parttimejob.auth.repository;

import com.fourseers.parttimejob.auth.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
