package com.rocket.laf.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.rocket.laf.dto.PictureDto;
import com.rocket.laf.mapper.BoardNoMapper;
import com.rocket.laf.service.BoardNoService;
import com.rocket.laf.service.impl.BoardNoServiceImpl;
import com.rocket.laf.service.impl.PictureServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rocket.laf.service.PictureService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/picture")
public class PictureController {

    private final PictureServiceImpl pictureService;
    private final BoardNoServiceImpl boardNoService;

//    @GetMapping("")
//    public String addPicture() {
//
//        return "";
//    }

    @ResponseBody
    @GetMapping("")
    public List<PictureDto> getMainPictureByBoardNo() {
//        List<PictureDto> mainPicList = pictureService.getMainPictureByBoardNo();
//        for (PictureDto pdto : mainPicList) {
//            String originPath = pdto.getStoredFilePath();
//            pdto.setStoredFilePath("/resources/img/communityBoard/" + originPath.substring(45));
//        }
        List<PictureDto> pictureDtoList = new ArrayList<>();
        List<PictureDto> mainPicList = pictureService.getMainPictureForCom();
        for (PictureDto pDto : mainPicList) {
            if (pDto.isPicExt() == true) {
                String picOriginPath = pDto.getStoredFilePath();
                pDto.setStoredFilePath("/resources/img/communityBoard/" + picOriginPath.substring(45));
                pictureDtoList.add(pDto);
            } else {
                pictureDtoList.add(pDto);
            }
        }
        return pictureDtoList;
    }

    @ResponseBody
    @GetMapping("/delete/{picNo}")
    public List<PictureDto> deletePicture(@PathVariable(name = "picNo") Long picNo) {//사진 삭제 후 리스트 반환
        String boardNo = boardNoService.getBoardNoByPicNo(picNo);
        pictureService.deleteSelectedPic(picNo);
        List<PictureDto> pictureList = pictureService.getAllPictureByBoardNo(boardNo);
        for (PictureDto pdto : pictureList) {
            String originPath = pdto.getStoredFilePath();
            pdto.setStoredFilePath("/resources/img/communityBoard/" + originPath.substring(45));
        }
//        for (PictureDto pdto : pictureList) {
//            System.out.println(pdto.getStoredFilePath());
//        }
        return pictureList;

    }

//    @GetMapping("/{boardNo}")
//    public List<PictureDto> getPicList(@PathVariable(name = "boardNo") String boardNo) {
//        List<PictureDto> pictureList = pictureService.getAllPictureByBoardNo(boardNo);
//        return pictureList;
//    }
}