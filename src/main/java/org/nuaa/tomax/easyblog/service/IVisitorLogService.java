package org.nuaa.tomax.easyblog.service;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.entity.VisitorLogEntity;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/3/3 18:57
 */
public interface IVisitorLogService {
    Response save(VisitorLogEntity visitorLogEntity);

    Response list();

    Response remove(Long id);
}
