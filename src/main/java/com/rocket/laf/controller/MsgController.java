package com.rocket.laf.controller;

import com.rocket.laf.dto.Message;
import com.rocket.laf.dto.MessageRoom;

import com.rocket.laf.service.impl.ChatServiceImpl;
import com.rocket.laf.service.impl.LostServiceImpl;
import com.rocket.laf.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;

import org.apache.catalina.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@RestController
@RequiredArgsConstructor
public class MsgController {

    private static ChatServiceImpl chatServiceImpl;
    private static UserServiceImpl userServiceImpl;
    private static LostServiceImpl lostServiceImpl;
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/comm/message")
    public void sendMessage(Message message, MessageRoom messageRoom) {
        sendingOperations.convertAndSend("/sub/comm/room/" + messageRoom.getRoomId(), message);
    }

    @PostMapping("/chat/ext/{roomId}")
    public void saveChatHist(@PathVariable String roomId, HttpServletRequest request) {
        long long_roomId = Long.parseLong(roomId);
        String[] chatHist = request.getParameterValues("chatHist");
        String chatHist4Save = chatHist.toString();
        chatServiceImpl.saveChatHist(chatHist4Save, long_roomId);
    }
}
