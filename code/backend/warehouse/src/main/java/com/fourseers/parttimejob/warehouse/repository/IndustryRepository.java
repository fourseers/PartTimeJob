package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.common.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndustryRepository extends JpaRepository<Industry, Integer> {

    List<Industry> findAll();
}
