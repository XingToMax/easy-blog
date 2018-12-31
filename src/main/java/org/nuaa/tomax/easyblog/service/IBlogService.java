package org.nuaa.tomax.easyblog.service;

import org.nuaa.tomax.easyblog.entity.BlogEntity;
import org.nuaa.tomax.easyblog.entity.Response;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/9 23:14
 */
public interface IBlogService {
    /**
     * create blog
     * @param blog
     * @return
     */
    Response create(BlogEntity blog);
}
