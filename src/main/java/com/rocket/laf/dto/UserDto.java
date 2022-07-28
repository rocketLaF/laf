package com.rocket.laf.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private long userNo;
    private String userName;
    private String userId;
    private String userEmail;
    private String userPw;
    private String userPhone;
    private String userBirth; //추가
    private String userSex;
    private String userAcc;
    private String userLocation;
    private int userTicket;
    private String userKeyword;
    private String userGrade;
}