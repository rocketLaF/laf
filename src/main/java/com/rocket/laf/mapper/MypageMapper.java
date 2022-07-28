package com.rocket.laf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.rocket.laf.dto.MypageDto;
import com.rocket.laf.dto.UserDto;

@Mapper
public interface MypageMapper {
    @Select( "SELECT * FROM User Order by userNo DESC")
    List<UserDto> selectlist();

    @Select(" SELECT * FROM User WHERE userId = #{userId}")
    UserDto selectOne(String userId);

    //회원가입 시 기입한 아이디, 이름, 지역
    @Select ( "SELECT * FROM User WHERE UserId = #{userId} and UserName = #{userName} and UserLocation = #{userLocation} and UserNo = #{userNo}")
    MypageDto userinfo(MypageDto mypagedto);
    
    //회원가입 시 선택한 프로필 사진
    @Select ("SELECT * FROM Picture Where picNo = #{picNo}")
    MypageDto selectOneforPicture(MypageDto dto);

    //프로필 사진 수정
    @Insert( "INSERT INTO Picture(picNo)VALUE = #{picNo}")
    String uploadPic(long picno);

    //동네설정
    @Insert( "INSERT INTO User(userLocation) VALUE = #{userLocation}")
    UserDto selectOneforLocation(String userlocation);

    

    @Update(" UPDATE User SET UserID = #{userId}, UserName = #{userName} WHERE UserNo = #{userNo}")
    int update(MypageDto dto);

   
}
