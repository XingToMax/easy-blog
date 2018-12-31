package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/30 19:14
 */
public interface IResourceRepository extends JpaRepository<ResourceEntity, Long> {

}
