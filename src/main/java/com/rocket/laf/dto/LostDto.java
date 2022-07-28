package com.rocket.laf.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LostDto {
    private String lBoardNo;
    private String lTitle;
    private String lContent;
    private Date lCreateDate;
    private String lIsModified;
    private String lLocation;
    private String lCategory;
    private String lQuestion;
    private String lAnswers;
    private String lAnswers1;
    private String lAnswers2;
    private String lAnswers3;
    private String lAnswers4;
    private long userNo;
    private long hashNo;
    private String storedFilePath;
    private long picNo;
}