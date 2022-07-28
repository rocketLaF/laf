package com.rocket.laf.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PictureDto {
    private long picNo;
    private String boardNo;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
    private boolean picRmd;
    private boolean picExt;
}