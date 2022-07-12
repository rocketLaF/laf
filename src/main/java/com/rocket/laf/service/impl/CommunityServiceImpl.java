package com.rocket.laf.service.impl;

import com.rocket.laf.dto.CommunityDto;
import com.rocket.laf.mapper.CommunityMapper;
import com.rocket.laf.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityMapper communityMapper;

    @Override
    public List<CommunityDto> getComBoardList() {
        return communityMapper.getComBoardList();
    }
}