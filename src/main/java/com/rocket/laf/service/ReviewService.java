package com.rocket.laf.service;

import java.util.List;

import com.rocket.laf.dto.ReviewDto;

public interface ReviewService {
    
    public int saveReview(ReviewDto dto);
    public List<ReviewDto> getReviewList(String userId);
    public ReviewDto selectReview(int reviewNo);



}
