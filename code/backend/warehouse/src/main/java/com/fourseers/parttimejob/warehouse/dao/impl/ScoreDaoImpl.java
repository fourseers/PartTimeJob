package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.common.entity.Score;
import com.fourseers.parttimejob.common.entity.ScoreKey;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.dao.ScoreDao;
import com.fourseers.parttimejob.warehouse.repository.ScoreRepository;
import com.fourseers.parttimejob.warehouse.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    @Override
    public Integer getOne(WechatUser user, int shopId) {
        if(!shopRepository.existsById(shopId))
            throw new RuntimeException("Shop doesn't exist.");
        ScoreKey scoreKey = new ScoreKey();
        scoreKey.setWechatUserId(user.getUserId());
        scoreKey.setShopId(shopId);
        Optional<Score> score = scoreRepository.findById(scoreKey);
        if(score.isPresent())
            return score.get().getScore();
        else
            return null;
    }
}
