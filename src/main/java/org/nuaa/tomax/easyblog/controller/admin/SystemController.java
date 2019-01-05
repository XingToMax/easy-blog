package org.nuaa.tomax.easyblog.controller.admin;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/6 0:31
 */
@Controller
@CrossOrigin
@RequestMapping("/admin/system")
public class SystemController {
    private final IUserService userService;


    public SystemController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/countData")
    public @ResponseBody
    Response countData() {
        return userService.systemInfoApi();
    }
}
