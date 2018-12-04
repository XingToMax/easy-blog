package org.nuaa.tomax.easyblog.exception;

import org.nuaa.tomax.easyblog.entity.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/3 21:40
 */
@RestControllerAdvice
public class SystemExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public Response handle(NullPointerException e) {
        return new Response(
                Response.NORMAL_EROOR_CODE,
                e.getMessage()
        );
    }
}
