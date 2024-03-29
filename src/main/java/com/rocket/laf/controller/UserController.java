package com.rocket.laf.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.rocket.laf.dto.PenaltyDto;
import com.rocket.laf.dto.UserDto;
import com.rocket.laf.service.TermsService;
import com.rocket.laf.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TermsService termsService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    // 로그인 창에서 회원가입으로 이동
    @GetMapping("/signUp")
    public String userSignUpBotton(Model model) {
        logger.info("------------------------Controller mapping 'signUp button call'");

        // 약관버전 설정
        int tVersion = 2;
        model.addAttribute("terms", termsService.selectOne(tVersion));
        System.out.println("-----------------------chk-----------------------" + model);

        return "user/terms";
    }

    //강제 주소창 입력시get 방식으로 전송되고 약관확인시 post로 전달
    @RequestMapping(value ="/signUpForm", method= {RequestMethod.GET, RequestMethod.POST})
    public String userSignUpFrom(HttpServletRequest request, Model model){
        logger.info("------------------------Controller mapping 'signUp form call'");

        // // jsp에서 넘어오는 파라미터 확인
        // Enumeration params = request.getParameterNames();
        // while(params.hasMoreElements()) {
        // String name = (String) params.nextElement();
        // System.out.print(name + " : " + request.getParameter(name) + " ");
        // }
        // System.out.println(request.getParameter("selectall").getClass().getName());

        String param = request.getParameter("selectall");

        if (param == null) {
            return "redirect:/user/login";
        } else if (param.equals("on")) {
            model.addAttribute("policyOn", request.getParameter("selectall"));
            return "user/signUp";
        } else {
            return "/user/login";
        }
    }

    @PostMapping("/chkDuplicatedId")
    @ResponseBody
    public Map<String, Boolean> chkDuplicatedId(@RequestBody Map<String, Object> idFromJson) {
        logger.info("------------------------Controller mapping 'chkDuplicatedId called'");

        System.out.println(idFromJson.get("id").toString());
        int res = userService.chkDuplicatedId(idFromJson.get("id").toString());
        Map<String, Boolean> jsonResponse = new HashMap<String, Boolean>();
        if (res == 0) { // 중복이 없을시
            jsonResponse.put("res", true);
        } else { // res == 1 중복이 있음
            jsonResponse.put("res", false);
        }

        System.out.println(res);

        return jsonResponse;
    }

    // 회원가입 페이지 입력정보 db에 저장
    @PostMapping("/regUser")
    public void resUser(UserDto userRegDto, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        logger.info("------------------------Controller mapping 'regUser'");
        
        // FIX: 001
        // String userBirth = combineBirth(request);
        // userRegDto.setUserBirth(userBirth);
        // String userLocation = combineLocation(request);
        // userRegDto.setUserLocation(userLocation);
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        userRegDto.setUserPw(pwdEncoder.encode(userRegDto.getUserPw()));

        int res = userService.regUser(userRegDto);

        if (res == 1) {
            alertToJsp(response, "회원가입을 축하드립니다.  로그인 페이지로 이동합니다.", 0, "/user/login");
            //return "redirect:/user/login";
        } else {
            alertToJsp(response, "예상치 못하게 에러가 발생했습니다. 회원가입을 다시진행해 주십시오", 1, "/user/login");
        }
        }
    

    @GetMapping("/signOut")
    public String userSignOut() {
        logger.info("------------------------Controller mapping 'signOut'");

        return "";
    }

    // 로그인 창에서 회원가입으로 이동
    @GetMapping("/login")
    public String userLogin(HttpServletRequest request, Authentication authentication) {
        logger.info("------------------------Controller mapping 'login'");

        AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
        if (trustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
            //login요청을 호출한 위치 저장 Referer: 어디서 참조했는가. 헤더에 url 표시됨.
            String uri = request.getHeader("Referer");
            //정상적인 login요청을 받고오면 index(메인페이지) 속성을 추가해준다.
            if (uri == null) {
                System.out.println("눌실행");
                request.getSession().setAttribute("index", request.getHeader(""));
                System.out.println(request.getSession().getAttribute("index"));
            }else if (!uri.contains("/login")){
                System.out.println("눌실행");
                request.getSession().setAttribute("index", request.getHeader("Referer"));
                System.out.println(request.getSession().getAttribute("index"));
            }
            System.out.println("컨트롤러 문제없음");
            return "/user/login";
        }else {
            System.out.println("세션살아있음");
            return "redirect:/";
        }
    }

    @PostMapping("/loginChk")
    public String loginChk(UserDto userdto) {
        logger.info("------------------------Controller mapping 'loginChk'");
        // System.out.println(userdto.toString());

        UserDto loggedinfo = userService.login(userdto);
        System.out.println(loggedinfo);

        return "user/terms";
    }

    @GetMapping("/socialLogin")
    public String userSocialLogin() {
        logger.info("------------------------Controller mapping 'socialLogin'");

        return "";
    }

    //3회이상 틀렸을시 ajax 호출받는 곳
    @PostMapping("/penalty/ajaxcall")
    @ResponseBody
    public Map<String, String> penaltySaveSession (@RequestBody Map<String, Object> penaltyJson, HttpServletRequest request, Authentication authentication){
        logger.info("------------------------Controller mapping /penalty/ajaxcall");
        
        HttpSession session = request.getSession();

        JSONArray penaltyArr = (JSONArray)session.getAttribute("penaltyObj");
        List<PenaltyDto> penaltyList = (ArrayList<PenaltyDto>) penaltyArr.get(0);
        System.out.println("최초 penalty list _______________ " + penaltyList);
        int newCnt = Integer.parseInt(penaltyJson.get("param2").toString());
        
        List<String> boardNoList = new ArrayList<>(); 
        for(int i = 0; i < penaltyList.size(); i++){
            if (penaltyList.get(i).getPBoardNo().equals(penaltyJson.get("param1"))){
                penaltyList.get(i).setPenaltyCnt(newCnt);
            }
            boardNoList.add(penaltyList.get(i).getPBoardNo());
        }

        Map<String, String> jsonResponse = new HashMap<String, String>();
        JSONArray penaltyObj = new JSONArray();
        System.out.println("boardNoList" + boardNoList);
        userService.deletePenalty(authentication.getName());
        
        if (boardNoList.contains(penaltyJson.get("param1"))){
            penaltyObj.add(penaltyList);
            System.out.println("________penaltyObj__유저컨트롤러_______________________" + penaltyObj);
            session.setAttribute("penaltyObj", penaltyObj);
            
            System.out.println("세션 이상없음");
            userService.updatePenalty(penaltyList);
            System.out.println("업데이트 페널티 이상없음");
            
            if (newCnt == 0){
                System.out.println("new cnt = 0");
                jsonResponse.put("res", "correct");
                return jsonResponse;
            }else if (newCnt < 3){
                System.out.println("new cnt = 3");
                jsonResponse.put("res", "wrong");
                return jsonResponse;
            }else{
                System.out.println("else");
                jsonResponse.put("res", "block");
                return jsonResponse;
            }
            
        }else{
            PenaltyDto element = setPenalty(penaltyJson, authentication);
            penaltyList.add(element);
            System.out.println("이후 penalty list _______________ " + penaltyList);
            penaltyObj.add(penaltyList);
            System.out.println("________penaltyObjElse__유저컨트롤러_______________________" + penaltyObj);
            session.setAttribute("penaltyObj", penaltyObj);

            userService.updatePenalty(penaltyList);

            // if (newCnt == 0){
            //     jsonResponse.put("res", "correct");
            //     return jsonResponse;
            // }else if (newCnt < 3){
            //     jsonResponse.put("res", "wrong");
            //     return jsonResponse;
            // }else{
            //     jsonResponse.put("res", "block");
            //     return jsonResponse;
            // }
            if (newCnt == 0){
                System.out.println("else new cnt = 0");
                jsonResponse.put("res", "correct");
                return jsonResponse;
            }else if (newCnt < 3){
                System.out.println("else new cnt = 3");
                jsonResponse.put("res", "wrong");
                return jsonResponse;
            }else{
                System.out.println("else else");
                jsonResponse.put("res", "block");
                return jsonResponse;
            }
        }

    }
    

    // FIX: 001
    // public String combineBirth(HttpServletRequest request) {
        //     int birthY_int = Integer.parseInt(request.getParameter("bY"));
        //     int birthM_int = Integer.parseInt(request.getParameter("bM"));
        //     int birthD_int = Integer.parseInt(request.getParameter("bD"));
        //     String birthM_str = "";
        //     String birthD_str = "";
        //     if (birthM_int >= 1 && birthM_int < 10) {
            //         birthM_str = "0" + Integer.toString(birthM_int);
            //     } else {
                //         birthM_str = request.getParameter("bM");
                //     }
                //     if (birthD_int >= 1 && birthD_int < 10) {
                    //         birthD_str = "0" + Integer.toString(birthD_int);
                    //     } else {
                        //         birthD_str = request.getParameter("bD");
                        //     }
                        //     String userBirth = request.getParameter("bY") + birthM_str + birthD_str;
                        //     return userBirth;
                        // }
                        
    // public String combineLocation(HttpServletRequest request) {
    //     String userLocation = request.getParameter("userLocation") + request.getParameter("userLocation_2nd");
    
    //     return userLocation;
    // }
    
    public void alertToJsp(HttpServletResponse response, String msg, int option, String redirect) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println("<script type='text/javascript'>");
        writer.println("alert('" + msg + "');");
        if (option == 1) {
            writer.println("history.back();");
        } else {
            writer.println("location.href='"+ redirect +"';");
        }
        writer.println("</script>");
        writer.flush();
    }
    
    public PenaltyDto setPenalty(@RequestBody Map<String, Object> penaltyJson, Authentication authentication) {
        PenaltyDto pDto = new PenaltyDto();
        pDto.setPBoardNo(penaltyJson.get("param1").toString());
        System.out.println("param1 작동함");
        pDto.setPenaltyCnt(Integer.valueOf(penaltyJson.get("param2").toString()));
        System.out.println("param2 작동함");
        pDto.setPUserId(authentication.getName());
        
        return pDto;
    }
    


    //테스트용
    @GetMapping("/secTestGgl")
    public String moveSecTest(@AuthenticationPrincipal User userInfo, Authentication auth) throws Exception {
        logger.info("------------------------Controller mapping 'user/secTest'");

        AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
        if (trustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
            
            
            System.out.println("익명의 사용자 _________ " +  userInfo);
            System.out.println("익명의 사용자 인증정보_________ " +  auth);
            
            return "/user/secTest";
        }else {
            System.out.println("로그인한 사용자_________ " +  userInfo);
            System.out.println("로그인한 사용자 아이디_________ " +  userInfo.getUsername());
            System.out.println("로그인한 사용자 인증정보_________ " +  auth);
            return "/user/secTestGgl";
        }

    }

    
}