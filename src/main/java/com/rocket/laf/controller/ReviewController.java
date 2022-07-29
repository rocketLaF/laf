package com.rocket.laf.controller;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nimbusds.oauth2.sdk.Response;
import com.rocket.laf.dto.ReviewDto;
import com.rocket.laf.service.ReviewService;

import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
@RequestMapping("/review")
public class ReviewController {
    
    private Logger logger = LoggerFactory.getLogger(ReviewController.class);
   
    @Autowired
    private ReviewService reviewService;

    //27일 완료
    @GetMapping("/write")
    public String reviewWritre(Model model, HttpServletRequest request) {
        
        logger.info("logger _______________ mapped to reviewWrite");
        
        //String reviewee = request.getParameter("1:1채팅에서 보내는 name값");
        model.addAttribute("revieweeId", "revieweeNeedtoBeChanged");
        
        return "/review/reviewWrite";
    }
    
    //28일 시작 (내가쓴 후기 목록보여주기)
    @GetMapping("")
    public String reviewList(@AuthenticationPrincipal User userInfo, Authentication auth, Model model) throws Exception {
        logger.info("logger _______________ mapped to reviewList");

        AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
        if (trustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
            return "redirect:";
        }else {
            System.out.println("로그인한 사용자 아이디_________ " +  userInfo.getUsername());
            String userId = userInfo.getUsername();
            List<ReviewDto> reviewListReturned = reviewService.getReviewList(userId);
            model.addAttribute("reviewList", reviewListReturned);

            return "/review/reviewList";
        }
    }

    //27일 완료
    @GetMapping("saveReview")
    public void saveReview(ReviewDto dto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("logger _______________ mapped to saveReview");

        int res = reviewService.saveReview(dto);
        
        UserController alert = new UserController();
        
        if (res == 1){
            alert.alertToJsp(response, "후기등록이 정상 처리 되었습니다.", 0, "/review");
        }else{
            alert.alertToJsp(response, "후기등록에 실패하였습니다. 다시시도하여 주세요", 1, "/review/write");
        }

   
    }

    //29일 시작
    @GetMapping(value="details")
    public String reviewDetails(int reviewNo, HttpServletRequest request, Model model) {
        logger.info("logger _______________ mapped to review/details");
        reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
        ReviewDto reviewDetail = reviewService.selectReview(reviewNo);
        model.addAttribute("reviewDetail", reviewDetail);

        System.out.println("review details __________ " + reviewDetail);

        return "/review/reviewDetail";
    }


    

    


}
