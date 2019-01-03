package org.nuaa.tomax.easyblog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
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
    @GetMapping(name = "")
    public String admin() {
        return "login";
    }

    /**
     * admin page
     */
    private static final List<String> ADMIN_PAGE_LIST = new ArrayList<String>(){{
        add("admin-index");
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

    /**
     * model page
     */
    private static final List<String> ADMIN_MODEL_PAGE = new ArrayList<String>() {{
        add("image_view");
        add("create_folder");
        add("create_class");
        add("blog_edit");
    }};

    @GetMapping("/page/{page}")
    public String welcome(@PathVariable(name = "page") String page, HttpSession session) {
        return ADMIN_PAGE_LIST.contains(page) ?
                session.getAttribute("User") != null ? page : "login" :
                "error/404";
    }

    @GetMapping("/page/model/{page}")
    public String modelPage(@PathVariable(name = "page") String page, HttpSession session, Long id, Model model) {
        model.addAttribute("id", id);
        return ADMIN_MODEL_PAGE.contains(page) ?
                session.getAttribute("User") != null ? "model/" + page : "login" :
                "error/404";
    }
}
