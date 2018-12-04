package org.nuaa.tomax.easyblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ToMax
 * @Description: 错误处理页
 * @Date: Created in 2018/12/4 18:50
 */
@Controller
@RequestMapping("/error")
public class ErrorController {
    /**
     * 将错误代码定向到相应的界面去
     * @param request
     * @return
     */
    public String errorPage(HttpServletRequest request) {
        int errorCode = (int) request.getAttribute("javax.servlet.error.status_code");
        if (errorCode >= 500) {
            return "error/500";
        }
        if (errorCode >= 400) {
            return "error/404";
        }
        return "error/error";
    }
}
