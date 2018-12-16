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
     * 创建博客
     * @param blog
     * @return
     */
    Response create(BlogEntity blog);
}
