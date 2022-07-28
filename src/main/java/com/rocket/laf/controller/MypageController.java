package com.rocket.laf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.rocket.laf.dto.MypageDto;
import com.rocket.laf.dto.UserDto;
import com.rocket.laf.service.MypageService;
import com.rocket.laf.service.UserService;
import com.rocket.laf.service.impl.MypageServiceImpl;
import com.rocket.laf.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/myPage") //로컬호스트 뜨고 마이페이지 경로로 가는데,
//URL 카멜케이스(?)
public class MypageController {
    
    @Autowired
    private MypageServiceImpl mypageServiceImpl;
    
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private MypageService mypageService;

    //기본 페이지 넘어오는 파라미터 X.
    //requsetParam으로도 해보기
    //유저넘버 받아오는 방법.

    // @GetMapping("")
    // public String vvv(){
    //     return "/mypage/myPage";
    // }

    @GetMapping("")
    public String viemypage(HttpServletRequest request, Model model, @AuthenticationPrincipal User userInfo, Authentication auth) throws Exception {
        AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
        if (trustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {


            System.out.println("익명의 사용자 _________ " +  userInfo);
            System.out.println("익명의 사용자 인증정보_________ " +  auth);

            return "/user/secTest";
        }else {
            System.out.println("로그인한 사용자_________ " +  userInfo.getUsername());
            System.out.println("로그인한 사용자 인증정보_________ " +  auth);

            String name = userInfo.getUsername();
            UserDto name1= mypageService.selectOne(name);
            
            model.addAttribute("dto", name1);
            System.out.println("name = " + name);

            
            return "/mypage/myPage";
        }
    }
    //     UserDto userid = mypageService.selectOne(userId);
    //     List<UserDto> uu = mypageService.selectList(userNo);
    //     model.addAttribute("userId",userid);
    //     Model asd = model.addAttribute("userId",uu);
    //     System.out.println("********************************************************************");
    //     System.out.println("Login ID =" + userid);
    //     System.out.println("UserDTO =" + asd);
    //     System.out.println("********************************************************************");
        
    //     return "mypage/myPage";

        // System.out.println("아이디 : " + userInfo);
        // // List<UserDto> uslist = mypageServiceImpl.selectOne(user_id);
        
        // model.addAttribute("ulist", );
        // System.out.println("********************************************************************");
        // System.out.println(uslist);
        // System.out.println("********************************************************************");
        
        // System.out.println("dto 내역들 = " + dto);
        // return "mypage/myPage";
    // // }
    
    @PostMapping("")
    public String mypage(MypageDto dto, Model model, MultipartFile file, HttpServletRequest request, @AuthenticationPrincipal User userInfo) throws Exception{
        mypageServiceImpl.picwrite(dto, file);
        String name = userInfo.getUsername();
        UserDto name1= mypageService.selectOne(name);
        
        model.addAttribute("dto", name1);
        System.out.println("name = " + name);
        String user_id = request.getParameter("userId");
        System.out.println("아이디 : " + user_id);
        // List<UserDto> uslist = mypageServiceImpl.selectOne(name1);
        
        // model.addAttribute("ulist", uslist);
        System.out.println("Controller file = " + file);
        System.out.println("Controller dto = " + dto);
        System.out.println("filename ? = " +dto.getOriginalFileName());
        System.out.println("filepath ? = " +dto.getStoredFilePath());
        
        model.addAttribute("img",dto);
        System.out.println("Postdto=" +dto);
        return "mypage/myPage";
    }

    


    /* *********************************************************************************************** */
     /* 페이지 전환할 것들 
     * 1. 내가 찾아준 내역, 2. 내 후기 모아보기
     * 마이페이지에서 내가 찾아준 내역으로 이동 */

    @GetMapping("/founddetail")
    public String MypagetoFounddetail(Model model){
        model.addAttribute("list",mypageService.selectList());
        
        return "/foundbyme/founddetail";
    }
    @GetMapping("/reviewdetail")
    public String FromMypagetoReviewAll(){
        return "/myreview/reviewdetail";
    }
    /* *********************************************************************************************** */
    //기능들git s
    //3. 동네설정 토글?
    
    //1. 프사
    @GetMapping("/uploadProfile")
    public String upLoadProfile(){
        return "/myPage";
    }

    // //2. 아이디 이름 수정
    // @GetMapping("/updateform")
    // public String updateForm(Model model, int userNo){
    //     model.addAttribute("dto",mypageServiceImpl.selectOne());
    //     return "/myPage";
    // }

    @PostMapping("/update")
    public String update(MypageDto dto){
        if(mypageServiceImpl.update(dto)>0){
            return "redirect:/myPage?userno=" +dto.getUserNo();
        }else{
            return "redirect:/myPage/updateform?userno=" +dto.getUserNo();
        }
    }

    //3. 동네설정 토글?
    // @GetMapping("/")

}
