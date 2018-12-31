package org.nuaa.tomax.easyblog.service;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.entity.UserEntity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 19:27
 */
public interface IUserService {

    /**
     * administrator login verify
     * @param username
     * @param password
     * @return
     */
    Response login(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * get user information
     * @param id
     * @return
     */
    UserEntity getUserInfo(long id);


    /**
     * update user information
     * @param user
     * @return
     */
    Response updateUserInfo(UserEntity user);

}
