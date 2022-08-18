package com.rocket.laf.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {

    private String username;
    private String content;
    private String roomId;
    private String chattime;
}
