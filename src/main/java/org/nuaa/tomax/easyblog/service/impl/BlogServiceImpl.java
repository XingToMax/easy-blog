package org.nuaa.tomax.easyblog.service.impl;

import org.nuaa.tomax.easyblog.entity.BlogEntity;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.repository.IBlogRepository;
import org.nuaa.tomax.easyblog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/9 23:20
 */
@Service
public class BlogServiceImpl implements IBlogService {

    private final IBlogRepository repository;

    @Autowired
    public BlogServiceImpl(IBlogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Response create(BlogEntity blog) {
        // TODO : 保存markdown文件
        blog = repository.save(blog);
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "创建博客成功",
                blog
        );
    }
}
