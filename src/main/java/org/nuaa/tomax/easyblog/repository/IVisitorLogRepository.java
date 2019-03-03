package org.nuaa.tomax.easyblog.repository;

import org.nuaa.tomax.easyblog.entity.VisitorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/3/3 18:57
 */
public interface IVisitorLogRepository extends JpaRepository<VisitorLogEntity, Long> {
}
