package com.rocket.laf.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rocket.laf.dto.LostDto;
import com.rocket.laf.dto.PictureDto;
import com.rocket.laf.service.impl.BoardNoServiceImpl;
import com.rocket.laf.service.impl.LostServiceImpl;
import com.rocket.laf.service.impl.PictureServiceImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class LafController {

    @Autowired
    private LostServiceImpl lostserviceImpl;
    @Autowired
    private PictureServiceImpl pictureServiceImpl;
    @Autowired
    private final BoardNoServiceImpl boardNoServiceImpl;

    private final static Logger logger = Logger.getGlobal();

    @GetMapping("")
    public String main(Model model) {
        List<LostDto> lostlist = lostserviceImpl.getLostBoardList();

        for (int i = 0; i < lostlist.size(); i++) {
            String originPath = lostlist.get(i).getStoredFilePath();
            lostlist.get(i).setStoredFilePath("/resources/" + originPath.substring(26));
        }
        model.addAttribute("lostlist", lostlist);
        // List<PictureDto> piclist =
        // pictureServiceImpl.getMainPictureByBoardNo(lostlist.get(0).getLBoardNo());
        // model.addAttribute("picture", piclist);
        return "index";
    }

    @GetMapping("/write")
    public String lostWrite() {
        return "lostcommunity/lostWrite";
    }

    @PostMapping("/write")
    public String lostWriteform(LostDto lostDto, MultipartHttpServletRequest multipartHttpServletRequest)
            throws Exception {
        String symbol = "l";
        long bnumbering = boardNoServiceImpl.getMaxlBoardNo() + 1;
        boardNoServiceImpl.addlBoardNo(bnumbering);

        String numbering = String.format("%08d", bnumbering);
        logger.log(Level.INFO, numbering);
        String lBoardNo = symbol + numbering;
        logger.log(Level.INFO, lBoardNo);

        lostDto.setLBoardNo(lBoardNo);
        lostserviceImpl.insertLostBoard(lostDto, multipartHttpServletRequest);
        return "redirect:/";
    }

    @GetMapping("/{lBoardNo}")
    public String LostDetail(@PathVariable(name = "lBoardNo") String lBoardNo, Model model) {
        String boardNo = lBoardNo;
        LostDto lolist = lostserviceImpl.getLostBoardOne(boardNo);
        List<PictureDto> piclist = pictureServiceImpl.getAllPictureByBoardNo(boardNo);
        for (int i = 0; i < piclist.size(); i++) {
            String originPath = piclist.get(i).getStoredFilePath();
            piclist.get(i).setStoredFilePath("/resources/" + originPath.substring(26));
        }
        model.addAttribute("picturelist", piclist);
        model.addAttribute("boardDetail", lolist);
        return "lostcommunity/lostDetail";
    }

    @PostMapping("/post_Quiz")
    public String LostCreate(HttpServletRequest req) {

        String answer = req.getParameter("ans");
        String bNo = req.getParameter("boardNo");

        logger.log(Level.INFO, answer);
        logger.log(Level.INFO, bNo);

        LostDto lost = lostserviceImpl.getLostBoardOne(bNo);
        if (lost.getLAnswers().equals(answer)) {
            return "redirect:/";
        } else {
            return "redirect:/lostDetail";
        }
    }

    @GetMapping("/update/{lBoardNo}")
    public String updatelBoardNo() {
        return "";
    }

    @PutMapping("/update/{lBoardNo}")
    public String updatelBoard() {
        return "";
    }

}