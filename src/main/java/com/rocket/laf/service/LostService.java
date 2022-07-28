package com.rocket.laf.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rocket.laf.dto.LostDto;

public interface LostService {
    List<LostDto> getLostBoardList();

    LostDto getLostBoardOne(String lBoardNo);

    int insertLostBoard(LostDto LostDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
}