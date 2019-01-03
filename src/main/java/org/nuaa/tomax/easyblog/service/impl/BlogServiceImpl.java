package org.nuaa.tomax.easyblog.service.impl;

import org.nuaa.tomax.easyblog.entity.BlogEntity;
import org.nuaa.tomax.easyblog.entity.Response;
import org.nuaa.tomax.easyblog.repository.IBlogRepository;
import org.nuaa.tomax.easyblog.service.IBlogService;
import org.nuaa.tomax.easyblog.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/9 23:20
 */
@Service
public class BlogServiceImpl implements IBlogService {

    private final IBlogRepository blogRepository;
    private static String blogRootPath;
    @Autowired
    public BlogServiceImpl(IBlogRepository blogRepository, Environment environment) {
        this.blogRepository = blogRepository;

        blogRootPath = environment.getProperty("source.blog.path", "source/blog");
    }

    @Override
    public Response create(BlogEntity blog) throws IOException {
        Long check = blogRepository.findBlogEntityByNameAndClassification(blog.getName(), blog.getClassification());
        if (check > 0) {
            return new Response(Response.SERVER_DATA_DUPLICATION, "name is exists");
        }
        // save markdown
        FileUtil.saveMarkdownFile(blog.getMarkdownContent(), blogRootPath + "/", blog.getName() + ".md");
        // save html
        FileUtil.saveMarkdownFile(blog.getHtmlContent(), blogRootPath + "/", blog.getName() + ".html");

        blog.setPath("/" + blog.getName());
        blog = blogRepository.save(blog);
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "创建博客成功",
                blog
        );
    }

    @Override
    public Response getBlogList(int beg, int end) {
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "get blog success",
                blogRepository.getBlogListByLimit(end, (beg - 1) * end),
                blogRepository.count()
        );
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Response updateBlogData(BlogEntity blog) {
        Long check = blogRepository.findBlogEntityByNameAndClassificationAndIdNot(blog.getName(), blog.getClassification(), blog.getId());
        if (check > 0) {
            return new Response(Response.SERVER_DATA_DUPLICATION, "name is exists");
        }

        BlogEntity data = blogRepository.findById(blog.getId()).orElseGet(() -> null);

        FileUtil.renameFile(blogRootPath, data.getName() + ".md", blog.getName() + ".md");
        FileUtil.renameFile(blogRootPath, data.getName() + ".html", blog.getName() + ".html");
        blog.setPath("/" + blog.getName());
        blogRepository.updateBlogData(blog.getName(),
                blog.getCover(),
                blog.getClassification(),
                blog.getLabels(),
                blog.getType(),
                blog.getBrief(),
                blog.getPath(),
                blog.getId());
        return new Response(
                Response.SUCCESS_CODE, "update blog data success");
    }

    @Override
    public Response deleteBlogById(Long id) {
        BlogEntity blog = blogRepository.findBlogEntityById(id);
        if (blog == null) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "blog not exists");
        }

        FileUtil.deleteFile(new File(blogRootPath + "/" + blog.getPath() + ".md"));
        FileUtil.deleteFile(new File(blogRootPath + "/" + blog.getPath() + ".html"));

        blogRepository.deleteById(id);
        return new Response(Response.SUCCESS_CODE, "delete blog success");
    }

    @Override
    public Response getBlogById(Long id) {
        BlogEntity blog = blogRepository.findBlogEntityById(id);
        if(blog == null) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "blog not exists");
        }

        // read content
        try {
            blog.setMarkdownContent(FileUtil.readMarkdownFile(blogRootPath + blog.getPath() + ".md"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Response<BlogEntity>(
                Response.SUCCESS_CODE,
                "get blog success",
                blog
        );
    }

    @Override
    public Response updateBlogContent(BlogEntity blog) throws IOException {
        BlogEntity blogData = blogRepository.findById(blog.getId()).orElseGet(() -> null);
        if (blogData == null) {
            return new Response(Response.SERVER_DATA_NOT_FOUND_ERROR, "blog not exists");
        }
        // update markdown
        FileUtil.saveMarkdownFile(blog.getMarkdownContent(), blogRootPath + "/", blogData.getName() + ".md");
        // update html
        FileUtil.saveMarkdownFile(blog.getHtmlContent(), blogRootPath + "/", blogData.getName() + ".html");
        return new Response(Response.SUCCESS_CODE, "update blog content success");
    }
}
