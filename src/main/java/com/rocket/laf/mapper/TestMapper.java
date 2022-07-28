package com.rocket.laf.mapper;

import com.rocket.laf.dto.TestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper {

    @Select(" SELECT ")
    List<TestDto> getComListData();
}