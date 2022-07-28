package com.rocket.laf.service.impl;

import com.rocket.laf.dto.TestDto;
import com.rocket.laf.mapper.TestMapper;
import com.rocket.laf.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestMapper testMapper;

    @Override
    public List<TestDto> getComListData() {
        return testMapper.getComListData();
    }
}