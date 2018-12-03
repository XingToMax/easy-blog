package org.nuaa.tomax.easyblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 15:43
 */
@Controller
@CrossOrigin
@RequestMapping("/")
public class BaseController {

    @GetMapping("/sayhello")
    public @ResponseBody
    String hello(String name) {
        return "hello, " + (name != null ? name : "friend");
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "friend");
        return "index";
    }

}
