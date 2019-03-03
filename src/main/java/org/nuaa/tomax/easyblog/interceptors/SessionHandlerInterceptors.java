package org.nuaa.tomax.easyblog.interceptors;

import org.nuaa.tomax.easyblog.entity.UserEntity;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/3/3 19:40
 */
public class SessionHandlerInterceptors implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        UserEntity user = (UserEntity) request.getSession().getAttribute("User");
//        if (user == null) {
//            response.sendRedirect("login.html");
//            return false;
//        }
        return true;
    }
}
