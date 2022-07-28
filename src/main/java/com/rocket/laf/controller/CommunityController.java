package com.rocket.laf.controller;

import com.rocket.laf.dto.HashTagDto;
import com.rocket.laf.dto.PictureDto;
import com.rocket.laf.dto.UserDto;
import com.rocket.laf.service.impl.*;
import com.rocket.laf.dto.CommunityDto;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cBoard")
public class CommunityController {

    private final CommunityServiceImpl communityService;
    private final PictureServiceImpl pictureService;
    private final HashTagServiceImpl hashTagService;
    private final UserServiceImpl userService;
    private final BoardNoServiceImpl boardNoService;

    @GetMapping("")
    public String getComBoardList(Model model) {
        List<CommunityDto> cDtoList = communityService.getComBoardList();
        // List<PictureDto> pictureDtoList = new ArrayList<>();
        // for (CommunityDto cDto : cDtoList) {
        // String boardNo = cDto.getCBoardNo();
        List<PictureDto> tmpPictureDtoList = pictureService.getMainPictureForCom();
        for (PictureDto pDto : tmpPictureDtoList) {
            if (pDto.isPicExt() == true) {
                String picOriginPath = pDto.getStoredFilePath();
                pDto.setStoredFilePath("/resources/img/communityBoard/" + picOriginPath.substring(45));
                // tmpPictureDtoList.add(pDto);
            } else {
                // tmpPictureDtoList.add(pDto);
                continue;
            }
        }
        // }
        model.addAttribute("cbList", cDtoList);
        model.addAttribute("picList", tmpPictureDtoList);
        return "/community/comBoardList";
    }

    @GetMapping("/write")
    public String openComBoardWrite() {
        return "/community/comBoardWrite";
    }

    @PostMapping("/write")
    public String writeComBoard(CommunityDto communityDto, MultipartHttpServletRequest multipartHttpServletRequest)
            throws Exception {
        String symbol = "com";
        long bnumbering = boardNoService.getMaxBoardNo() + 1;
        boardNoService.addBoardNo(bnumbering);
        String numbering = String.format("%08d", bnumbering);
        String comBoardNo = symbol + numbering;
        communityDto.setCBoardNo(comBoardNo);
        communityService.writeComBoard(communityDto, multipartHttpServletRequest);
        return "redirect:/cBoard";
    }

    @GetMapping("/{cBoardNo}")
    public String getComBoardDetail(@PathVariable(name = "cBoardNo") String cBoardNo, Model model) throws Exception {
        CommunityDto comDto = communityService.getComBoardDetail(cBoardNo);
        long hashNo = comDto.getHashNo();
        long userNo = comDto.getUserNo();
        List<PictureDto> pictureDtoList = new ArrayList<>();
        List<PictureDto> picList = pictureService.getAllPictureByBoardNo(cBoardNo);
        for (PictureDto pDto : picList) {
            if (pDto.isPicExt() == true) {
                String picOriginPath = pDto.getStoredFilePath();
                pDto.setStoredFilePath("/resources/img/communityBoard/" + picOriginPath.substring(45));
                pictureDtoList.add(pDto);
            } else {
                pictureDtoList.add(pDto);
            }
        }
        HashTagDto hashTagDto = hashTagService.getHashTagById(hashNo);
        UserDto userDto = userService.getUserById(userNo);
        model.addAttribute("cbDetail", comDto);
        model.addAttribute("pDetail", picList);
        model.addAttribute("hDetail", hashTagDto);
        model.addAttribute("uDetail", userDto);
        return "/community/comBoardDetail";
    }

    @GetMapping("/update/{cBoardNo}")
    public String updateComBoardForm(@PathVariable(name = "cBoardNo") String cBoardNo, Model model) throws Exception {
        CommunityDto comDto = communityService.getComBoardDetail(cBoardNo);
        long hashNo = comDto.getHashNo();
        long userNo = comDto.getUserNo();
        List<PictureDto> picList = pictureService.getAllPictureByBoardNo(cBoardNo);
        for (PictureDto pdto : picList) {
            String originPath = pdto.getStoredFilePath();
            pdto.setStoredFilePath("/resources/img/communityBoard/" + originPath.substring(45));
        }
        HashTagDto hashTagDto = hashTagService.getHashTagById(hashNo);
        UserDto userDto = userService.getUserById(userNo);
        model.addAttribute("cbDetail", comDto);
        model.addAttribute("pDetail", picList);
        model.addAttribute("hDetail", hashTagDto);
        model.addAttribute("uDetail", userDto);
        return "/community/comBoardUpdate";
    }

    @PostMapping("/update/{cBoardNo}")
    public String updateComBoardDetail(@PathVariable(name = "cBoardNo") String cBoardNo, CommunityDto communityDto,
            MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        communityService.updateComBoardDetail(communityDto, multipartHttpServletRequest);
        return "redirect:/cBoard/" + cBoardNo;
    }

    // @PreAuthorize("hasRole('USER')")
    @GetMapping("/delete/{cBoardNo}")
    public String deleteComBoardDetail(@PathVariable(name = "cBoardNo") String cBoardNo) {
        communityService.deleteComBoardDetail(cBoardNo);
        return "redirect:/cBoard";
    }
}