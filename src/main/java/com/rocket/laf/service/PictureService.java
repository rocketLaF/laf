package com.rocket.laf.service;

import java.util.List;

import com.rocket.laf.dto.PictureDto;

public interface PictureService {
    // 모든 사진 가져오기
    List<PictureDto> getAllPictureByBoardNo(String boardNo);

    //커뮤니티 대표사진 한장 가져오기
    List<PictureDto> getMainPictureForCom();

    //로스트 대표사진 한장 가져오기
    List<PictureDto> getMainPictureForLost();

    // 선택된 사진 삭제하기
    void deleteSelectedPic(long picNo);
}