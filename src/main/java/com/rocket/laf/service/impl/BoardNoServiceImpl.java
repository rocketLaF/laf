package com.rocket.laf.service.impl;

import com.rocket.laf.mapper.BoardNoMapper;
import com.rocket.laf.service.BoardNoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardNoServiceImpl implements BoardNoService {

    private final BoardNoMapper boardNoMapper;

    @Override
    public long getMaxBoardNo() {
        return boardNoMapper.getMaxBoardNo();
    }

    @Override
    public long addBoardNo(long numbering) {
        return boardNoMapper.addBoardNo(numbering);
    }

    @Override
    public long getMaxlBoardNo() {
        return boardNoMapper.getMaxlBoardNo();
    }

    @Override
    public long addlBoardNo(long lBoardNo) {
        return boardNoMapper.addlBoardNo(lBoardNo);
    }

    @Override
    public String getBoardNoByPicNo(long picNo) {
        return boardNoMapper.getBoardNoByPicNo(picNo);
    }

}