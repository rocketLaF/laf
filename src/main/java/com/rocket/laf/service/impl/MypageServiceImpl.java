package com.rocket.laf.service.impl;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rocket.laf.dto.MypageDto;
import com.rocket.laf.dto.UserDto;
import com.rocket.laf.mapper.MypageMapper;
import com.rocket.laf.service.MypageService;

@Service
public class MypageServiceImpl implements MypageService {
    
    @Autowired
    private MypageMapper mypageMapper;
    
    
    @Override
    public MypageDto userinfo(MypageDto mypagedto) {
        return mypageMapper.userinfo(mypagedto);
    }
    
    @Override
    public MypageDto selectOneforPicture(MypageDto dto) {
        
        return mypageMapper.selectOneforPicture(dto);
    }
    
    @Override
    public UserDto selectOne(String userId) {
        return mypageMapper.selectOne(userId);
    }
    
    @Override
    public void picwrite(MypageDto dto, MultipartFile file) throws Exception{
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\img\\profile";

        // UUID uuid = UUID.randomUUID();
        // 그냥 앞에 식별자 넣어주는 코드 랜덤.

        // String fileName = uuid + "_" + file.getOriginalFilename();
        String fileName = file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);
        

        System.out.println("filename : " + fileName);
        System.out.println("projectPath : " + projectPath);
        // System.out.println("uuid : " + uuid);
        System.out.println("saveFile : " + saveFile);
        
        dto.setOriginalFileName(fileName);
        dto.setStoredFilePath("/img/profile/" + fileName);
        dto.getOriginalFileName();
        System.out.println("get file name : " +dto.getOriginalFileName());
    }
    

    @Override
    public UserDto selectOneforLocation(String userlocation) {
        return mypageMapper.selectOneforLocation(userlocation);
    }

    @Override
    public int update(MypageDto dto) {
        return mypageMapper.update(dto);
    }

    @Override
    public List<UserDto> selectList() {
        return mypageMapper.selectlist();
    }
    
}
