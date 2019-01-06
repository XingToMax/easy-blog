package org.nuaa.tomax.easyblog.service.impl;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.entity.UserEntity;
import org.nuaa.tomax.easyblog.entity.view.SystemCountDataView;
import org.nuaa.tomax.easyblog.entity.view.UserView;
import org.nuaa.tomax.easyblog.repository.*;
import org.nuaa.tomax.easyblog.service.IUserService;
import org.nuaa.tomax.easyblog.util.FileUtil;
import org.nuaa.tomax.easyblog.util.PasswordEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 19:30
 */
@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;
    private final IBlogRepository blogRepository;
    private final ILinkRepository linkRepository;
    private final IClassificationRepository classificationRepository;
    private final IImageRepository imageRepository;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, IBlogRepository blogRepository, ILinkRepository linkRepository, IClassificationRepository classificationRepository, IImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.linkRepository = linkRepository;
        this.classificationRepository = classificationRepository;
        this.imageRepository = imageRepository;
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

    @Override
    public Response userInfoApi() {
        List<String> labelList = blogRepository.getLabels();
        Set<String> labelSet = new HashSet<>();
        for (String label : labelList) {
            for (String cell : label.split(";")) {
                labelSet.add(cell);
            }
        }
        return new Response<UserView>(
                Response.SUCCESS_CODE,
                "get user data success",
                new UserView(
                    userRepository.getUsername(1L),
                    blogRepository.count(),
                    classificationRepository.count(),
                    (long) labelSet.size(),
                    0L,
                    "/images/avatar.png",
                    linkRepository.getFriendLinkEntities()
                )
        );
    }

    @Override
    public Response updateUserInfo(UserEntity user, MultipartFile file) throws IOException {
        FileUtil.saveFile(file, this.getClass().getResource("/").getPath() + "static/images/", "avatar.png");
        return new Response(Response.SUCCESS_CODE, "update user info success");
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Response changePassword(String origin, String current) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserEntity user = userRepository.findById(1L).orElseGet(()->null);
        if (user != null && PasswordEncryptUtil.validPassword(origin, user.getPassword())) {
            userRepository.updatePassword(PasswordEncryptUtil.getEncryptedPwd(current), 1L);
            return new Response(Response.SUCCESS_CODE, "update password success");
        }
        return new Response(Response.NORMAL_EROOR_CODE, "origin password wrong");
    }

    @Override
    public Response systemInfoApi() {
        List<String> labelList = blogRepository.getLabels();
        Set<String> labelSet = new HashSet<>();
        for (String label : labelList) {
            for (String cell : label.split(";")) {
                labelSet.add(cell);
            }
        }
        return new Response<SystemCountDataView>(
                Response.SUCCESS_CODE,
                "get user data success",
                new SystemCountDataView(
                        blogRepository.count(),
                        classificationRepository.count(),
                        (long) labelSet.size(),
                        imageRepository.count(),
                        0L
                )
        );
    }
}
