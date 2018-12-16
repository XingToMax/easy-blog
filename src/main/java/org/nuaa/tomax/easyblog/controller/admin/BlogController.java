package org.nuaa.tomax.easyblog.controller.admin;

import org.nuaa.tomax.easyblog.entity.BlogEntity;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/9 23:12
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/blog")
public class BlogController {

    private final IBlogService blogService;

    @Autowired
    public BlogController(IBlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("")
    public Response createBlog(BlogEntity blog) {
        // TODO : 输入校验
        return blogService.create(blog);
    }
}
