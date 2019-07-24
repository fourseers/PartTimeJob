package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.common.entity.CV;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.dao.CVDao;
import com.fourseers.parttimejob.warehouse.dto.CVDto;
import com.fourseers.parttimejob.warehouse.dto.NewCVDto;
import com.fourseers.parttimejob.warehouse.projection.CVBriefProjection;
import com.fourseers.parttimejob.warehouse.repository.CVRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CVDaoImpl implements CVDao {

    @Autowired
    private CVRepository cvRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<CVBriefProjection> getCVListByUser(WechatUser user) {
        return cvRepository.getCVByUserId(user.getUserId());
    }

    @Override
    public CVDto getOne(String cvId) {
        Optional<CV> cvOptional = cvRepository.findById(cvId);
        if(!cvOptional.isPresent())
            return null;
        return modelMapper.map(cvOptional.get(), CVDto.class);
    }

    @Override
    public boolean updateOne(CVDto cvDto, WechatUser user) {
        CV cv = cvRepository.findById(cvDto.getCvId()).orElse(null);
        if(cv == null) throw new RuntimeException("Invalid cv id.");
        if(cv.getUserId() != user.getUserId()) throw new RuntimeException("CV does not belong to user.");

        CV newCV = modelMapper.map(cvDto, CV.class);
        if(newCV.getStatement() == null ) newCV.setStatement(cv.getStatement());
        if(cvDto.getExperiences() == null) newCV.setExperiences(cv.getExperiences());
        newCV.setUserId(user.getUserId());
        cvRepository.save(newCV);
        return true;
    }

    @Override
    public boolean addOne(NewCVDto newCvDto, WechatUser user) {
        CV cv = modelMapper.map(newCvDto, CV.class);
        cv.setUserId(user.getUserId());
        cvRepository.save(cv);
        return true;
    }

    @Override
    public boolean deleteOne(String cvId) {
        if(!cvRepository.existsById(cvId))
            return false;
        cvRepository.deleteById(cvId);
        return true;
    }

    @Override
    public boolean belongsTo(String cvId, WechatUser user) {
        CV cv = cvRepository.findById(cvId).orElse(null);
        if(cv == null)
            return false;
        return cv.getUserId() == user.getUserId();
    }
}
