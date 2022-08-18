package com.rocket.laf.service.impl;

import com.rocket.laf.dto.MessageRoom;
import com.rocket.laf.dto.UserDto;
import com.rocket.laf.mapper.ChatMapper;
import com.rocket.laf.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMapper chatMapper;

    @Override
    public List<MessageRoom> getAllChatRoom(String username) {
        return chatMapper.getAllChatRoom(username);
    }

    @Transactional
    @Override
    public void createChatRoom(String boardNo, long userNo) {
        chatMapper.createChatRoom(boardNo, userNo);
    }

    @Override
    public long getRoomIdByuserNo(long userNo, String boardNo) {
        return chatMapper.getRoomIdByuserNo(userNo, boardNo);
    }

    @Override
    public int chkChatRoomExist(String boardNo, long userNo) {
        return chatMapper.chkChatRoomExist(boardNo, userNo);
    }

    @Override
    public List<MessageRoom> getAllChatRoomByUser(long userNo) {
        return chatMapper.getAllChatRoomByUser(userNo);
    }

    @Override
    public List<MessageRoom> getAllChatRoomByUserName(String userName) {
        return chatMapper.getAllChatRoomByUserName(userName);
    }

    @Override
    public MessageRoom getRoomByRoomId(long roomId) {
        return chatMapper.getRoomByRoomId(roomId);
    }

    @Override
    public UserDto getUserInfoByRoomId(long roomId) {
        return chatMapper.getUserInfoByRoomId(roomId);
    }

    @Override
    public String getChatHistory(long roomId) {
        return chatMapper.getChatHistory(roomId);
    }

    @Override
    public void saveChatHist(String content, long roomId) {
        chatMapper.saveChatHist(content, roomId);
    }
}
