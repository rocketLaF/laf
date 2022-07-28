package com.rocket.laf.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rocket.laf.dto.MypageDto;
import com.rocket.laf.dto.UserDto;

public interface MypageService {

    public List<UserDto> selectList();
    UserDto selectOne(String userId);
    
    public MypageDto userinfo(MypageDto dto);
    public MypageDto selectOneforPicture(MypageDto dto);
    public void picwrite(MypageDto dto, MultipartFile file) throws Exception;
    
    UserDto selectOneforLocation(String userlocation);
    public int update(MypageDto dto);
}
