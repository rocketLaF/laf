package com.rocket.laf.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.rocket.laf.dto.TermsDto;

@Mapper
public interface TermsMapper {
    
    @Select(" select * from Terms where tVersion=#{tVersion}")
    TermsDto selectOne(int tVersion);

}