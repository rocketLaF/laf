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
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.SystemPropertyUtils;

import com.rocket.laf.dto.UserDto;
import com.rocket.laf.dto.UserSocialDto;
import com.rocket.laf.mapper.UserMapper;
import com.rocket.laf.service.UserService;

import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.RequiredArgsConstructor;

@Service
public class UserServiceImpl extends DefaultOAuth2UserService
        implements UserService, UserDetailsService, AuthenticationSuccessHandler {

    @Autowired
    private UserMapper userMapper;

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
    public UserSocialDto chkUserSocialData(String socialEmail) {
        return userMapper.chkUserSocialData(socialEmail);
    }

    @Override
    public int regUserSocial(UserSocialDto dto) {
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
        User secReturnUser = new User(secUser.getUserId(), secUser.getUserPw(), auth);
        System.out.println(secReturnUser);
        return secReturnUser;
    }

    // Form login success handler
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        System.out.println("잘연결됬습니깡? 넵");

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
        response.sendRedirect(uri);
    }
    // Spring Security form login end

    // Spring Security OAuth2 ggl login start
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 오바라이드라 함수이름 못바꾼다.
        // 1. email 추출하여 한번 있는사용자인지 훑어보고 있으면 dto로 가져오고
        OAuth2User oauth2User = super.loadUser(userRequest); // OAuth2 통해서 로그인한 유저정보 전체
        String userEmail = oauth2User.getAttribute("email");
        UserSocialDto dtoRes = chkUserSocialData(userEmail);

        // 2. 없으면 usersocialDto에 묻지고 따지지도 않고 가입시킨다음 다시 1번으로가서 Dto 가져오고
        if (dtoRes == null) { // 사용자 정보가 없다는 뜻
            UserSocialDto dto = transferToDto(userRequest, oauth2User); // 아래 DTO 함수실행
            int res = regUserSocial(dto);
            if (res == 1) { // db에 회원정보 저장 성공
                dtoRes = chkUserSocialData(dto.getSocialEmail());
            } else { // db에 회원정보 저장 실패
                throw new OAuth2AuthenticationException("404: 소셜로그인으로 서비스 회원가입중 시스템 장애가 발생했습니다.");
            }
        }
        // 3. OAuth2User로 dto를 번환한다음 아래 최종 loaduser를 DefaultOAuth2User타입으로 반환한다.
        OAuth2User res = transOAuth2User(dtoRes);

        // 나중에 약관은 동의 처리받는것이 좋겠다.

        return res;
    }

    private UserSocialDto transferToDto(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        UserSocialDto userSocialDto = new UserSocialDto();
        userSocialDto.setSocialProvider(userRequest.getClientRegistration().getRegistrationId());
        // userSocialDto.setSocialId(oauth2User.getAttribute("sub"));
        userSocialDto.setSocialId("8888888888");
        userSocialDto.setSocialEmail(oauth2User.getAttribute("email"));
        userSocialDto.setSocialName(oauth2User.getAttribute("name"));
        return userSocialDto;
    }

    private OAuth2User transOAuth2User(UserSocialDto dto) {

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("provider", dto.getSocialProvider());
        userDetails.put("sub", dto.getSocialId());
        userDetails.put("email", dto.getSocialEmail());
        userDetails.put("username", dto.getSocialName());

        Collection<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_USER"));

        OAuth2User transedOA2User = new DefaultOAuth2User(auth, userDetails, "username");

        return transedOA2User;
    }
    // Spring Security OAuth2 ggl login end

}