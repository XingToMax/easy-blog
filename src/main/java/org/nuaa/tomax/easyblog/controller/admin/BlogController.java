package org.nuaa.tomax.easyblog.controller.admin;

import org.nuaa.tomax.easyblog.entity.BlogEntity;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @PostMapping
    public Response createBlog(BlogEntity blog) throws IOException {
        return blogService.create(blog);
    }

    @GetMapping
    public Response listBlog(int page, int limit) {
        return blogService.getBlogList(page, limit);
    }

    @GetMapping("/{id}")
    public Response getBlogById(@PathVariable(name = "id") Long id) {
        return blogService.getBlogById(id);
    }

    @PutMapping("/data")
    public Response updateBlogData(BlogEntity blog) {
        return blogService.updateBlogData(blog);
    }

    @PutMapping("/content")
    public Response updateBlogContent(BlogEntity blog) throws IOException {
        return blogService.updateBlogContent(blog);
    }

    @PutMapping("/state")
    public Response updateBlogState(Integer state, Long id) {
        return blogService.updateBlogState(id, state);
    }

    @DeleteMapping
    public Response deleteBlogById(Long id) {
        return blogService.deleteBlogById(id);
    }
}
