package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.common.entity.DateInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateInformationRepository extends JpaRepository<DateInformation, Long> {
}
