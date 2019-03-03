package org.nuaa.tomax.easyblog.service.impl;

import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.entity.VisitorLogEntity;
import org.nuaa.tomax.easyblog.repository.IVisitorLogRepository;
import org.nuaa.tomax.easyblog.service.IVisitorLogService;
import org.springframework.stereotype.Service;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/3/3 19:12
 */
@Service
public class VisitorLogServiceImpl implements IVisitorLogService {
    private final IVisitorLogRepository logRepository;

    public VisitorLogServiceImpl(IVisitorLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public Response save(VisitorLogEntity visitorLogEntity) {
        logRepository.save(visitorLogEntity);
        return new Response(
                Response.SUCCESS_CODE,
                "success"
        );
    }

    @Override
    public Response list() {
        return new Response<VisitorLogEntity>(
                Response.SUCCESS_CODE,
                "success",
                logRepository.findAll()
        );
    }

    @Override
    public Response remove(Long id) {
        logRepository.deleteById(id);
        return new Response(
                Response.SUCCESS_CODE,
                "success"
        );
    }
}
