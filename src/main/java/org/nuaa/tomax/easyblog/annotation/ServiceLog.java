package org.nuaa.tomax.easyblog.annotation;

import java.lang.annotation.*;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/3/3 15:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ServiceLog {
    String name() default "";
    String description() default "";
    String method() default "GET";
}
