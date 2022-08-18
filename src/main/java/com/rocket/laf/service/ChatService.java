package com.rocket.laf.service;

import com.rocket.laf.dto.MessageRoom;
import com.rocket.laf.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public interface ChatService {
    List<MessageRoom> getAllChatRoom(String username);

    void createChatRoom(String username, long boardNo);

    long getRoomIdByuserNo(long userNo, String boardNo);

    int chkChatRoomExist(String boardNo, long userNo);

    List<MessageRoom> getAllChatRoomByUser(long userNo);

    List<MessageRoom> getAllChatRoomByUserName(String userName);

    MessageRoom getRoomByRoomId(long roomId);

    UserDto getUserInfoByRoomId(long roomId);

    String getChatHistory(long roomId);

    void saveChatHist(String content, long roomId);
}
