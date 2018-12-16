package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/9 23:13
 */
public interface IBlogRepository extends JpaRepository<BlogEntity, Long> {

}
