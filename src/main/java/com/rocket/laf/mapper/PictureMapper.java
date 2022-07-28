package com.rocket.laf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.rocket.laf.dto.PictureDto;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PictureMapper {

    @Select(" SELECT * FROM Picture WHERE boardNo = #{boardNo} ORDER BY picNo DESC ")
    List<PictureDto> getAllPictureByBoardNo(String boardNo);

    @Select(" SELECT * FROM Picture WHERE LOCATE('com', boardNo) GROUP BY boardNo ORDER BY picNo DESC ")
    List<PictureDto> getMainPictureForCom();

    @Select(" SELECT * FROM Picture WHERE LOCATE('l', boardNo) GROUP BY boardNo ORDER BY picNo DESC ")
    List<PictureDto> getMainPictureForLost();

    @Update(" UPDATE Picture Set picRmd = 0 WHERE picNo = #{picNo} ")
    void deleteSelectedPic(long picNo);

    @Insert(" INSERT INTO Picture (picNo, boardNo, createdDate) VALUES (null, #{boardNo}, now()) ")
    void insertPicBoardNo(String boardNo);
}