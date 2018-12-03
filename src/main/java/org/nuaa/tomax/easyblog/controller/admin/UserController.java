package org.nuaa.tomax.easyblog.controller.admin;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.entity.UserEntity;
import org.nuaa.tomax.easyblog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 17:30
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {
    private final IUserService userService;


    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public @ResponseBody
    String save(UserEntity user) {
        return "success";
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Response getUser(@PathVariable(name = "id") long id) {
        return new Response<UserEntity>(
                Response.SUCCESS_CODE,
                "获取user成功",
                userService.getUserInfo(id)
        );
    }
}
