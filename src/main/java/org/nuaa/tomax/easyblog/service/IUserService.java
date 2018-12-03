package org.nuaa.tomax.easyblog.service;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.entity.UserEntity;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 19:27
 */
public interface IUserService {

    /**
     * 登录验证
     * @param username
     * @param password
     * @return
     */
    Response login(String username, String password);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    UserEntity getUserInfo(long id);


    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Response updateUserInfo(UserEntity user);

}
