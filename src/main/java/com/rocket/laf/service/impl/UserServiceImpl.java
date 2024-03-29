package com.rocket.laf.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rocket.laf.dto.MessageRoom;
import com.rocket.laf.mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.rocket.laf.common.DefaultOAuth2UserExtention;
import com.rocket.laf.common.UserExtension;
import com.rocket.laf.dto.PenaltyDto;
import com.rocket.laf.dto.UserDto;
import com.rocket.laf.mapper.PenaltyMapper;
import com.rocket.laf.mapper.UserMapper;
import com.rocket.laf.service.UserService;


@Service
public class UserServiceImpl extends DefaultOAuth2UserService
        implements UserService, UserDetailsService, AuthenticationSuccessHandler {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PenaltyMapper penaltyMapper;
    @Autowired
    private ChatMapper chatMapper;

    @Override
    public UserDto login(UserDto dto) {
        return userMapper.login(dto);
    }

    @Override
    public int regUser(UserDto dto) {
        return userMapper.register(dto);
    }

    @Override
    public UserDto getUserById(long userNo) {
        return userMapper.getUserById(userNo);
    }

    @Override
    public int chkDuplicatedId(String idFromJson) {
        return userMapper.chkDuplicatedId(idFromJson);
    }

    @Override
    public UserDto chkUserSocialData(String userEmail) {
        return userMapper.chkUserSocialData(userEmail);
    }

    @Override
    public UserDto getUserInfoById(String username) {
        return userMapper.getUserInfoById(username);
    }

    @Override
    public Long getUserNoById(String username) {
        return userMapper.getUserNoById(username);
    }

    @Override
    public List<MessageRoom> getAllChatRoomByUserName(String userName) {
        return chatMapper.getAllChatRoomByUserName(userName);
    }

    @Override
    public int regUserSocial(UserDto dto) {
        return userMapper.regUserSocial(dto);
    }

    // Spring Security form login Start
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto secUser = userMapper.secLogin(username);

        if (secUser == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }
        List<GrantedAuthority> auth = new ArrayList<>();
        if (secUser.getUserGrade().equals("BASIC")) {
            auth.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        User secReturnUser = new UserExtension(secUser.getUserId(), secUser.getUserPw(), auth, secUser.getUserNo());
        System.out.println(secReturnUser);
        
        return secReturnUser;
    }

    // Form login success handler
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
        System.out.println("Session ID ________ " + web.getSessionId());
        System.out.println("인증 name : " + authentication.getName());

        String uri = "/";

        // 접근권한이없는 경우 시큐리티가 로그인 페이지로 강제이동시킬때 사용자가 직전에 권한이있던 요청한 정보 저장
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        // 로그인 버튼을 눌러 접속한경우
        String dataFromIndex = (String) request.getSession().getAttribute(("index"));

        // 세션에 "index" 값이 있다는 뜻이면 로그인버튼통해서 들어옴. 메모리관리를 위해 지우자
        if (dataFromIndex != null) {
            request.getSession().removeAttribute("index");
        }
        if (savedRequest != null) {
            uri = savedRequest.getRedirectUrl();
        } else if (dataFromIndex != null && dataFromIndex.equals("")) {
            uri = dataFromIndex;
        }

        HttpSession session = request.getSession();
        List<PenaltyDto> penaltyList = new ArrayList<>();
        penaltyList = penaltyMapper.getCurPenalty(authentication.getName());
        System.out.println("_______penaltyList_________________________" + penaltyList);

        JSONArray penaltyObj = new JSONArray();
        penaltyObj.add(penaltyList);
        
        System.out.println("________penaltyObj_________________________" + penaltyObj);
        session.setAttribute("penaltyObj", penaltyObj);

        response.sendRedirect(uri);
    }
    // Spring Security form login end

    // Spring Security OAuth2 ggl login start
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 오바라이드라 함수이름 못바꾼다.
        // 1. email 추출하여 한번 있는사용자인지 훑어보고 있으면 dto로 가져오고
        OAuth2User oauth2User = super.loadUser(userRequest); // OAuth2 통해서 로그인한 유저정보 전체
        System.out.println("oauth2User _____ " + oauth2User);
        String userEmail = oauth2User.getAttribute("email");
        UserDto dtoRes = chkUserSocialData(userEmail);
        System.out.println("dtoRes _____ " + dtoRes);

        // 2. 없으면 usersocialDto에 묻지고 따지지도 않고 가입시킨다음 다시 1번으로가서 Dto 가져오고
        if (dtoRes == null) { // 사용자 정보가 없다는 뜻
            UserDto dto = transferToDto(userRequest, oauth2User); // 아래 DTO 함수실행
            System.out.println("dto _____ " + dto);
            int res = regUserSocial(dto);
            System.out.println("res _____ " + res);
            if (res == 1) { // db에 회원정보 저장 성공
                dtoRes = chkUserSocialData(dto.getUserEmail());
            } else { // db에 회원정보 저장 실패
                throw new OAuth2AuthenticationException("404: 소셜로그인으로 서비스 회원가입중 시스템 장애가 발생했습니다.");
            }
        }
        // 3. OAuth2User로 dto를 번환한다음 아래 최종 loaduser를 DefaultOAuth2User타입으로 반환한다.
        OAuth2User res = transOAuth2User(dtoRes);

        // 나중에 약관은 동의 처리받는것이 좋겠다.
        System.out.println("OAuth2User result _________________ " + res);

        return res;
    }

    private UserDto transferToDto(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        UserDto userDto = new UserDto();
        userDto.setSocialProvider(userRequest.getClientRegistration().getRegistrationId());
        // userSocialDto.setSocialId(oauth2User.getAttribute("sub"));
        String maxUserNo = Integer.toString(userMapper.getMaxUserNo());
        String milSec = Long.toString(System.currentTimeMillis()); 
        userDto.setUserId("s_"+maxUserNo+"_"+milSec);
        userDto.setUserEmail(oauth2User.getAttribute("email"));
        userDto.setUserName(oauth2User.getAttribute("name"));
        return userDto;
    }

    private OAuth2User transOAuth2User(UserDto dto) {

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("provider", dto.getSocialProvider());
        userDetails.put("sub", dto.getUserId());
        userDetails.put("email", dto.getUserEmail());
        userDetails.put("username", dto.getUserName());

        Collection<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_USER"));

        OAuth2User transedOA2User = new DefaultOAuth2UserExtention(auth, userDetails, "username");

        return transedOA2User;
    }
    // Spring Security OAuth2 ggl login end

    @Override
    public void deletePenalty(String userId) {
       penaltyMapper.deletePenalty(userId);
    }

    @Override
    public void updatePenalty(List<PenaltyDto> list) {
        try {
            penaltyMapper.updatePenalty(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}