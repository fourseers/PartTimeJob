package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.common.entity.Score;
import com.fourseers.parttimejob.common.entity.ScoreKey;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.dao.ScoreDao;
import com.fourseers.parttimejob.warehouse.repository.ScoreRepository;
import com.fourseers.parttimejob.warehouse.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScoreDaoImpl implements ScoreDao {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public boolean submitOne(WechatUser user, int shopId, int score) {
        if(!shopRepository.existsById(shopId))
            throw new RuntimeException("Shop doesn't exist.");

        ScoreKey scoreKey = new ScoreKey();
        Score scoreEntity = new Score();
        scoreKey.setShopId(shopId);
        scoreKey.setWechatUserId(user.getUserId());
        scoreEntity.setScoreId(scoreKey);
        // you have to set both composite id and related entities
        // explicitly in code
        // otherwise hibernate will get confused
        scoreEntity.setWechatUser(user);
        scoreEntity.setShop(shopRepository.getOne(shopId));
        scoreEntity.setScore(score);
        scoreRepository.save(scoreEntity);
        return true;
    }
}
