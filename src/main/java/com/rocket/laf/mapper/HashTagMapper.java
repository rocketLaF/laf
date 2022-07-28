package com.rocket.laf.mapper;

import com.rocket.laf.dto.HashTagDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HashTagMapper {

    @Select(" SELECT * FROM HashTag WHERE hashNo = #{hashNo} ")
    HashTagDto getHashTagById(long hashNo);
}