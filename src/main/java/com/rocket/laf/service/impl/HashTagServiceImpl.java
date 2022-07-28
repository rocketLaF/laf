package com.rocket.laf.service.impl;

import com.rocket.laf.dto.HashTagDto;
import com.rocket.laf.mapper.HashTagMapper;
import com.rocket.laf.service.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {

    private final HashTagMapper hashTagMapper;

    @Override
    public HashTagDto getHashTagById(long hashNo) {
        return hashTagMapper.getHashTagById(hashNo);
    }
}