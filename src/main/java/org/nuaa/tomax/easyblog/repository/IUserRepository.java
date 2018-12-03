package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 17:29
 */
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

}
