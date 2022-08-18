package com.rocket.laf.controller;

import com.rocket.laf.dto.LostDto;
import com.rocket.laf.dto.MessageRoom;
import com.rocket.laf.dto.UserDto;
import com.rocket.laf.service.impl.ChatServiceImpl;
import com.rocket.laf.service.impl.LostServiceImpl;
import com.rocket.laf.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comm")
public class MsgRoomController {

    private final ChatServiceImpl chatService;
    private final LostServiceImpl lostService;
    private final UserServiceImpl userService;

    @GetMapping("/room/enter/{roomId}")
    public ModelAndView roomEnter(@PathVariable String roomId, Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        String accessUser = authentication.getName();
        long roomId4Chat = Long.parseLong(roomId);
        String chatHistory = chatService.getChatHistory(roomId4Chat);
//        String[] stepChatHistArray = chatHistory.split()
//        ArrayList<String> chatHistArrayList = new ArrayList<String>(Arrays.asList(stepChatHisArray));
        String url = "redirect://localhost:3000/?roomId="+roomId+"&&userName="+accessUser;
        mv.setViewName(url);
//        mv.addObject("chatHist", chatHistArrayList);
        return mv;
    }

    @ResponseBody
    @GetMapping("/room/{roomId}")
    public MessageRoom roomInfo(@PathVariable String roomId) {
        long roomId_to_long = Long.parseLong(roomId);
        MessageRoom msg = chatService.getRoomByRoomId(roomId_to_long);
//        System.out.println(msg.getRoomId());
//        System.out.println(msg.getUserNo());
        return msg;
    }

    @GetMapping("/rooms/{userNo}")
    public String getAllChatRoomByUser(@PathVariable long userNo, Model model, Authentication authentication) {
        List<MessageRoom> messageRooms = chatService.getAllChatRoomByUser(userNo);
        for (MessageRoom messageRoom : messageRooms) {
            String title = lostService.getLostBoardTitle(messageRoom.getBoardNo());
            messageRoom.setTitle(title);
        }
        model.addAttribute("roomList", messageRooms);
        return "/chat/roomList";
    }


//    @ResponseBody
//    @GetMapping("/room/{roomId}/{boardNo}/{accessUserName}")
//    public int chkChatRoomExist(@RequestParam String roomId, @RequestParam String accessUserName, @RequestParam String boardNo) {
//        MessageRoom roomByUserInfo = chatService.getRoomByRoomId(roomId);
//        Long userNo = roomByUserInfo.getUserNo();
//        UserDto userById = userService.getUserById(userNo);
//        System.out.println(userById.getUserName());
//        if (userById.getUserName() == loginUserName) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }

//    @ResponseBody
//    @GetMapping("/rooms")
//    public String getAllChatRoomByUser(Model model) {
//        System.out.println(userNo+"시작");
//        long userNo_long = Long.parseLong(userNo);
//        System.out.println(userNo_long+"후");
//        List<MessageRoom> messageRooms = chatServiceImpl.getAllChatRoomByUser();
//        System.out.println(messageRooms+"chk");
//        model.addAttribute("roomList", messageRooms);
//        return "/chat/roomList";
//    }
//    @GetMapping("/chat/rooms")
//    public List<MessageRoom> getAllChatRoomByUser(Model model, Authentication authentication) {
//        String username = authentication.getName();
//        UserDto userDto = userServiceImpl.getUserInfoById(username);
//        List<MessageRoom> messageRooms = chatServiceImpl.getAllChatRoomByUser(userDto.getUserNo());
//        model.addAttribute("roomList", messageRooms);
//        return messageRooms;
//    }
}



