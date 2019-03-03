package org.nuaa.tomax.easyblog.aspect;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.entity.VisitorLogEntity;
import org.nuaa.tomax.easyblog.service.IVisitorLogService;
import org.nuaa.tomax.easyblog.util.RequestUtils;
import org.slf4j.event.Level;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.slf4j.event.Level.*;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/3/3 15:27
 */
@Aspect
@Component
@Order(-5)
@Log
@Slf4j
public class ServiceLogAspect {
    private final IVisitorLogService logService;

    public ServiceLogAspect(IVisitorLogService logService) {
        this.logService = logService;
    }

    @Pointcut("@annotation(org.nuaa.tomax.easyblog.annotation.ServiceLog)")
    public void controllerAspect() {}

    @Around("controllerAspect()")
    public Response recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
//        SystemLog systemLog = new SystemLog();
        Object proceed = null ;
        //获取session中的用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().getAttribute("user");

        proceed = joinPoint.proceed();
        Response response = (Response) proceed;

        String ip = RequestUtils.getRequestIp(request);
        String os = RequestUtils.getOs(request);
        String browser = RequestUtils.getBrowser(request);
        logService.save(new VisitorLogEntity(ip, browser, os, constructDescription(joinPoint), response.getCode()));
        return response;
    }

    @AfterThrowing(pointcut = "controllerAspect()",throwing="e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable{
        log.info(e.getMessage());
    }

    private String constructDescription(ProceedingJoinPoint joinPoint) {
        String description = "";
        description += joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "()";
        return description;
    }
}
