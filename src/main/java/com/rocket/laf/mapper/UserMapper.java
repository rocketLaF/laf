package com.rocket.laf.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.rocket.laf.dto.UserDto;
import com.rocket.laf.dto.UserSocialDto;

@Mapper
public interface UserMapper {

    @Select( "select * from User where userId=#{userId} and userPw=#{userPw}" )
    UserDto login(UserDto userDto);

    @Insert( "insert into User (userName, userId, userEmail, userPw, userPhone, userBirth, userSex, userAcc, userLocation, userTicket, userKeyword) VALUES ( #{userName}, #{userId}, #{userEmail}, #{userPw}, #{userPhone}, #{userBirth}, #{userSex}, #{userAcc}, #{userLocation}, #{userTicket}, #{userKeyword} )")
    int register(UserDto dto);
    //리턴타입 매퍼함수이름(매개변수타입 사용할매개변수명)

    @Select(" SELECT * FROM User WHERE userNo = #{userNo} ")
    UserDto getUserById(long userNo);

    @Select("select EXISTS (SELECT userNo from User where userId=#{idFromJson})")
    int chkDuplicatedId(String idFromJson);

    //security login
    @Select( "select * from User where userId=#{userId} " )
    UserDto secLogin(String userId);

    @Select( "select * from UserSocial where socialEmail = #{socialEmail}" )
    UserSocialDto chkUserSocialData(String socialEmail);

    @Insert( "insert into UserSocial (socialProvider, socialId, socialEmail, socialName) values (#{socialProvider}, #{socialId}, #{socialEmail}, #{socialName})" ) 
    int regUserSocial(UserSocialDto dto);

}