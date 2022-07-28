package com.rocket.laf.common;

import com.rocket.laf.dto.PictureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileUtils {

    private final ResourceLoader resourceLoader;

    public List<PictureDto> parseFileInfo(String boardNo, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        if (ObjectUtils.isEmpty(multipartHttpServletRequest)) {
            return null;
        }
        List<PictureDto> fileList = new ArrayList<>();
        //시간을 생성하는 이유는 저장될 파일 이름이 겹치지 않게 하기위해서 시간으로 파일명을 바꿔서 저장합니다.
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        //저장되는 Path 설정입니다. 각자의 경로가 다르기에 시스템상으로 Path.of....을 사용하여 경로를 구하고 마지막 저장될 파일 경로를 따로 기입해줍니다.
        String rootPath = "";
        if (boardNo.contains("com")) {
            rootPath = "src/main/resources/static/img/communityBoard/";
        } else if (boardNo.contains("l")) {
            rootPath = "src/main/resources/static/img/lostBoard/";
        } else if (boardNo.contains("mp")) {
            rootPath = "src/main/resources/static/img/myPageProfile/";
        }

        String path = rootPath + current.format(format);
        File file = new File(path);
        if (file.exists() == false) {
            file.mkdir();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        String newFileName, originalFileExtension, contentType;

        while (iterator.hasNext()) {
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for (MultipartFile multipartFile : list) {
                if (multipartFile.isEmpty() == false) {
                    contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) {
                        break;
                    } else {
                        //저장되는 파일의 형태를 확인 후, 해당하는 확장자명으로 저장합니다.
                        if (contentType.contains("image/jpeg")) {
                            originalFileExtension = ".jpg";
                        } else if (contentType.contains("image/png")) {
                            originalFileExtension = ".png";
                        } else if (contentType.contains("image/gif")) {
                            originalFileExtension = ".gif";
                        } else {
                            break;
                        }
                    }

                    newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
                    PictureDto pictureDto = new PictureDto();
                    pictureDto.setBoardNo(boardNo);
                    pictureDto.setFileSize(multipartFile.getSize());
                    pictureDto.setOriginalFileName(multipartFile.getOriginalFilename());
                    pictureDto.setStoredFilePath(path + "/" + newFileName);
                    fileList.add(pictureDto);

                    file = new File(path + "/" + newFileName);
                    multipartFile.transferTo(file);
                }
            }
        }
        return fileList;
    }
}