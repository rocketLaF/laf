package com.rocket.laf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocket.laf.dto.TermsDto;
import com.rocket.laf.mapper.TermsMapper;
import com.rocket.laf.service.TermsService;

@Service
public class TermsServiceImpl implements TermsService{

    @Autowired
    private TermsMapper termsMapper;

    @Override
    public TermsDto selectOne(int tVersion) {

        return termsMapper.selectOne(tVersion);
    }

    
}