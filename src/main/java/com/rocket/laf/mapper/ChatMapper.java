package com.rocket.laf.mapper;

import com.rocket.laf.dto.MessageRoom;
import com.rocket.laf.dto.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChatMapper {

    @Select(" SELECT roomId FROM Message WHERE username= #{username} ")
    List<MessageRoom> getAllChatRoom(String username);

    @Insert(" INSERT INTO Message (boardNo, userNo) VALUES (#{boardNo}, #{userNo}) ")
    void createChatRoom(String boardNo, long userNo);

    @Select(" SELECT roomId FROM Message WHERE userNo=#{userNo} and boardNo=#{boardNo} ")
    long getRoomIdByuserNo(long userNo, String boardNo);

    @Select(" SELECT COUNT(*) FROM (SELECT * FROM Message WHERE boardNo=#{boardNo} and userNo=#{userNo}) CHAT_EXIST ")
    int chkChatRoomExist(String boardNo, long userNo);

    @Select(" SELECT * FROM Message WHERE userNo = #{userNo} ")
    List<MessageRoom> getAllChatRoomByUser(long userNo);

    @Select(" SELECT * FROM Message m INNER JOIN User u WHERE u.userNo = m.userNo and u.userName= #{userName} ")
    List<MessageRoom> getAllChatRoomByUserName(String userName);

    @Select(" SELECT roomId,userNo FROM Message WHERE roomId = #{roomId} ")
    MessageRoom getRoomByRoomId(long roomId);

    @Select(" SELECT userNo, boardNo")
    UserDto getUserInfoByRoomId(long roomId);
//    @Update(" UPDATE ")

    @Select(" SELECT content FROM Message WHERE roomId = #{roomId} ")
    String getChatHistory(long roomId);

    @Update(" UPDATE Message SET content = #{content} WHERE roomId = #{roomId} ")
    void saveChatHist(String content, long roomId);
}
