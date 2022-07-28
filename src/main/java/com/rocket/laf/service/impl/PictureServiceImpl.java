package com.rocket.laf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocket.laf.dto.PictureDto;
import com.rocket.laf.mapper.PictureMapper;
import com.rocket.laf.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public List<PictureDto> getAllPictureByBoardNo(String boardNo) {
        return pictureMapper.getAllPictureByBoardNo(boardNo);
    }

    @Override
    public List<PictureDto> getMainPictureForCom() {
        return pictureMapper.getMainPictureForCom();
    }

    @Override
    public List<PictureDto> getMainPictureForLost() {
        return pictureMapper.getMainPictureForLost();
    }

    @Override
    public void deleteSelectedPic(long picNo) {
        pictureMapper.deleteSelectedPic(picNo);
    }

}