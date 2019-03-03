package org.nuaa.tomax.easyblog.controller.admin;

import lombok.extern.java.Log;
import org.nuaa.tomax.easyblog.annotation.ServiceLog;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.entity.UserEntity;
import org.nuaa.tomax.easyblog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 17:30
 */
@Log
@Controller
@CrossOrigin
@RequestMapping("/admin/user")
public class    UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 处理登录请求
     * @param username
     * @param password
     * @param session
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/login")
    public @ResponseBody
    @ServiceLog
    Response login(String username, String password, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Response response = userService.login(username, password);
        if (response.getCode() == Response.SUCCESS_CODE) {
            log.info(username + " login success");
            Long userId = ((UserEntity) response.getData()).getId();
            session.setAttribute("User", response.getData());
        } else {
            log.info(username + "login fail");
        }
        return response;
    }


    @GetMapping("")
    @ServiceLog
    public @ResponseBody
    Response getUser() {
        return new Response<UserEntity>(
                Response.SUCCESS_CODE,
                "获取user成功",
                userService.getUserInfo(1L)
        );
    }

    @PutMapping("/avatar")
    @ServiceLog
    public @ResponseBody
    Response updateAvatar(MultipartFile file) throws IOException {
        return userService.updateUserInfo(null, file);
    }
}
