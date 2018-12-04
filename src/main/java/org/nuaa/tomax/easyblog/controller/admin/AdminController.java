package org.nuaa.tomax.easyblog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/4 20:45
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String admin() {
        return "index";
    }

    // 页面管理
    private static final List<String> ADMIN_PAGE_LIST = new ArrayList<String>(){{
        add("welcome");
        add("blog");
        add("classification");
        add("label");
        add("folder");
        add("images");
        add("resources");
        add("user");
        add("link");
        add("visitor");
        add("comment");
        add("blacklist");
        add("system");
        add("log");
    }};


    @GetMapping("/{page}")
    public String welcome(@PathVariable(name = "page") String page) {
        if (ADMIN_PAGE_LIST.contains(page)) {
            return page;
        }
        return "error/404";
    }
}
