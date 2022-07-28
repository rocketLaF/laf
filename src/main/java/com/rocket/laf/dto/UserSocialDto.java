package com.rocket.laf.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSocialDto {

    private int socialUserNo;
    private String socialProvider;
    private String socialId;
    private String socialEmail;
    private String socialName;
    private String socialGrade;


}