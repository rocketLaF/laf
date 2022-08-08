package com.rocket.laf.service.impl;

import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@ServerEndpoint(value="/chat")
public class WebSocketChat {

    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    @OnMessage
    public void onMessage(String msg, Session session) throws IOException {
        System.out.println("received msg : " + msg);
        for (Session s : clients) {
            System.out.println("send data : " + msg);
            s.getBasicRemote().sendText(msg);
        }
    }

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("open session : " + s.toString());
        if (!clients.contains(s)) {
            clients.add(s);
            System.out.println("session open : " + s);
        } else {
            System.out.println("이미 연결된 session입니다.");
        }
    }

    @OnClose
    public void onClose(Session s) {
        System.out.println("session close : " + s);
        clients.remove(s);
    }
}