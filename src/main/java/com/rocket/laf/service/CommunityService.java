package com.rocket.laf.service;

import com.rocket.laf.dto.CommunityDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface CommunityService {

    List<CommunityDto> getComBoardList();

    void writeComBoard(CommunityDto communityDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

    CommunityDto getComBoardDetail(String cBoardNo) throws Exception;

    void updateComBoardDetail(CommunityDto communityDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

    int deleteComBoardDetail(String cBoardNo);

    String getLastCBoardNo();
}