package com.rocket.laf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.rocket.laf.dto.ReviewDto;

@Mapper
public interface ReviewMapper {

    @Insert( "insert into Review (rUserId, rRevieweeId, rContent, rOption, rScore) VALUES ( #{rUserId}, #{rRevieweeId}, #{rContent}, #{rOption}, #{rScore})")
    int saveReview(ReviewDto dto);

    @Select( "select * from Review where rUserId=#{userId} ")
    List<ReviewDto> getReviewList(String userId);

    @Select ( "select * from Review where reviewNo=#{reviewNo}" )
    ReviewDto selectReview(int reviewNo);

}
