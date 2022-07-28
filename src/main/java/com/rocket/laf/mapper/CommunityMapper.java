package com.rocket.laf.mapper;

import com.rocket.laf.dto.CommunityDto;
import com.rocket.laf.dto.PictureDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommunityMapper {

        // @Select("SELECT c.cBoardNo, c.cTitle, c.cCreateDate, c.cLocation,
        // c.cIsModified, p.storedFilePath, p.picNo, p.picRmd " +
        // "FROM Community c " +
        // "INNER JOIN Picture p " +
        // "ON c.cBoardNo = p.boardNo " +
        // "GROUP BY p.boardNo ORDER BY p.picNo DESC")
        @Select(" SELECT * FROM Community ORDER BY cBoardNo DESC ")
        List<CommunityDto> getComBoardList();

        @Insert(" INSERT INTO Community " +
                        "(cBoardNo, cTitle, cContent, cCreateDate, cLocation, cCategory, userNo, hashNo) " +
                        "VALUES (CONCAT('com', LPAD((SELECT MAX(cBoardNo) FROM BoardNo),8,'0')),#{cTitle},#{cContent},now(),#{cLocation},#{cCategory},1,1) ")
        @Options(keyProperty = "cBoardNo")
        void writeComBoard(CommunityDto communityDto);

        @Select(" SELECT * " +
                        "FROM Community " +
                        "WHERE cBoardNo = #{cBoardNo} ")
        CommunityDto getComBoardDetail(String cBoardNo);

        @Update(" UPDATE Community " +
                        "SET cTitle=#{cTitle}, cContent=#{cContent}, cIsModified=1 " +
                        "WHERE cBoardNo = #{cBoardNo} ")
        void updateComBoardDetail(CommunityDto communityDto);

        @Delete(" DELETE FROM c, p " +
                        "USING Community c " +
                        "INNER JOIN Picture p " +
                        "ON c.cBoardNo = p.boardNo " +
                        "WHERE p.boardNo = #{cBoardNo} ")
        int deleteComBoardDetail(String cBoardNo);

        @Select(" SELECT MAX(cBoardNo) FROM Community ")
        String getLastCBoardNo();

        @Insert({ "<script>" +
                        "INSERT INTO Picture " +
                        "(boardNo,originalFileName, storedFilePath, fileSize, createdDate, picExt) VALUES" +
                        "<foreach collection='list' item='item' separator=','>" +
                        "(" +
                        "#{item.boardNo}," +
                        "#{item.originalFileName}," +
                        "#{item.storedFilePath}," +
                        "#{item.fileSize}," +
                        "NOW()," +
                        "1" +
                        ")" +
                        "</foreach> " +
                        "</script>" })
        void writeComBoardFileList(List<PictureDto> list) throws Exception;

}