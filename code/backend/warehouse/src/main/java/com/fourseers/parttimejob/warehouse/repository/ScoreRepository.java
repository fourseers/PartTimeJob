package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.common.entity.Score;
import com.fourseers.parttimejob.common.entity.ScoreKey;
import com.fourseers.parttimejob.common.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScoreRepository extends JpaRepository<Score, ScoreKey> {
    @Query("select avg(s.score) from Score s where s.shop = ?1")
    Float getShopAverageScore(Shop shop);
}
