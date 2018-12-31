package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/30 17:34
 */
public interface IImageRepository extends JpaRepository<ImageEntity, Long>{
}
