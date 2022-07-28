package com.rocket.laf.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MypageDto {
    //유저 관련 Dto / 유저 아이디, 이름, 지역, 
    private long userNo;
    private String userId;
    private String userName;
    private String userLocation;
    //동네 설정은 프론트단에서 해보자.
     
    //프로필 사진 관련 Dto
    
    // private String fileSize;
    private long picNo;
    private String storedFilePath;
    private List<PictureDto> picList;
    private String originalFileName;
    
    //누적사례금, 사례횟수, 사례금 잔액관련 Dto .
    private int payNo;
    private int pReward;
    
    
    // @   프론트단에서 눌러볼 아이들   @
    //1. 동네설정 ( 글 넣기 ) 2. 내가 찾아준내역, 3. 내 후기 모아보기는 클릭으로 페이지 이동.

    


}
