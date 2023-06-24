package com.frankzhou.community.controller;

import com.frankzhou.community.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 主页控制器 主页展示内容接口
 * @date 2023-06-18
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private UserService userService;
}
