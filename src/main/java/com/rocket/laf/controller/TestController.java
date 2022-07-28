package com.rocket.laf.controller;

import com.rocket.laf.dto.TestDto;
import com.rocket.laf.service.impl.CommunityServiceImpl;
import com.rocket.laf.service.impl.PictureServiceImpl;
import com.rocket.laf.service.impl.TestServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final CommunityServiceImpl communityService;
    private final PictureServiceImpl pictureService;
    private final TestServiceImpl testService;

    @GetMapping("/")
    public TestDto comListTest() {
        testService.getComListData();
        return null;
    }
}