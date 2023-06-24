package com.frankzhou.community.controller;

import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.user.UserLoginDTO;
import com.frankzhou.community.model.dto.user.UserRegisterDTO;
import com.frankzhou.community.model.vo.UserProfileVO;
import com.frankzhou.community.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 用户管理控制器 用户操作相关接口
 * @date 2023-06-18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/send/code")
    public ResultDTO<String> sendCode(@RequestParam("phone") String phone) {
        return null;
    }

    @PostMapping("/register/account")
    public ResultDTO<Boolean> registerByAccount(@RequestBody UserRegisterDTO registerDTO) {
        return null;
    }

    @PostMapping("/register/phone")
    public ResultDTO<Boolean> registerByPhone(@RequestBody UserRegisterDTO registerDTO) {
        return null;
    }

    @PostMapping("/login/account")
    public ResultDTO<Boolean> loginByAccount(@RequestBody UserLoginDTO loginDTO) {
        return null;
    }

    @PostMapping("/login/phone")
    public ResultDTO<Boolean> loginByPhone(@RequestBody UserLoginDTO loginDTO) {
        return null;
    }

    @PostMapping("/reset/password")
    public ResultDTO<Boolean> resetPassword() {
        return null;
    }

    @PostMapping("/edit")
    public ResultDTO<Boolean> editPersonalProfile() {
        return null;
    }

    @GetMapping("/profile/{userId}")
    public ResultDTO<UserProfileVO> getUserProfile(@PathVariable("userId") Long userId) {
        return null;
    }
}
