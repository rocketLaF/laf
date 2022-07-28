package com.rocket.laf.service;

public interface BoardNoService {
    long getMaxBoardNo();

    long addBoardNo(long numbering);

    long getMaxlBoardNo();

    long addlBoardNo(long lBoardNo);

    String getBoardNoByPicNo(long picNo);
}