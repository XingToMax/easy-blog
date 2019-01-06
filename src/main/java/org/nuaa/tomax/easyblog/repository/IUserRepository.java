package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 17:29
 */
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    UserEntity findByUsername(String username);

    /**
     * get user name
     * @param id
     * @return
     */
    @Query(value = "select username from user where id = ?1", nativeQuery = true)
    String getUsername(Long id);

    @Modifying
    @Query(value = "update user set password = ?1 where id = ?2", nativeQuery = true)
    void updatePassword(String password, Long id);
}
