package com.rocket.laf.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto {
    private String cBoardNo;
    private String cTitle;
    private String cContent;
    private LocalDateTime cCreateDate;
    private boolean cIsModified;
    private String cLocation;
    private String cCategory;
    private long userNo;
    private long hashNo;
//    private String storedFilePath;
//    private List<PictureDto> picList;
//    private boolean picRmd;
}