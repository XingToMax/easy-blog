package org.nuaa.tomax.easyblog.service.impl;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.entity.UserEntity;
import org.nuaa.tomax.easyblog.repository.IUserRepository;
import org.nuaa.tomax.easyblog.service.IUserService;
import org.nuaa.tomax.easyblog.util.PasswordEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 19:30
 */
@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response login(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null && PasswordEncryptUtil.validPassword(password, user.getPassword())) {
            user.setPassword("");
            return new Response<UserEntity>(
                    Response.SUCCESS_CODE,
                    "登录验证成功",
                    user
            );
        }
        return new Response(
                Response.INPUT_ERROR_CODE,
                "用户名或密码错误"
        );
    }

    @Override
    public UserEntity getUserInfo(long id) {
        return userRepository.findById(id).orElseGet(() -> null);
    }

    @Override
    public Response updateUserInfo(UserEntity user) {
        UserEntity update = userRepository.saveAndFlush(user);
        return new Response<UserEntity>(
                Response.SUCCESS_CODE,
                "更新user成功",
                update);
    }
}
