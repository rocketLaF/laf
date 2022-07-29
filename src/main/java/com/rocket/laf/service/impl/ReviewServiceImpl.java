package com.rocket.laf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocket.laf.dto.ReviewDto;
import com.rocket.laf.mapper.ReviewMapper;
import com.rocket.laf.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public int saveReview(ReviewDto dto) {
        return reviewMapper.saveReview(dto);
    }

    @Override
    public List<ReviewDto> getReviewList(String userId) {
        return reviewMapper.getReviewList(userId);
    }

    @Override
    public ReviewDto selectReview(int reviewNo) {
        return reviewMapper.selectReview(reviewNo);
    }
    
}
