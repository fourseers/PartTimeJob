package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.common.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

}
